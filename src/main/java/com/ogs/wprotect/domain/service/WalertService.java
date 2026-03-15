package com.ogs.wprotect.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ogs.wprotect.domain.Walert;
import com.ogs.wprotect.domain.Wcontact;
import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.repository.WalertRepository;
import com.ogs.wprotect.persistence.crud.WalertaCrudRepository;
import com.ogs.wprotect.persistence.entity.AlertStatus;
import com.ogs.wprotect.persistence.entity.CloseReason;
import com.ogs.wprotect.persistence.entity.Walerta;
import com.ogs.wprotect.persistence.mapper.WalertMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WalertService {
    @Autowired
    private WalertRepository walertRepository;
    
    @Autowired
    private WalertaCrudRepository walertaCrudRepository;
    
    @Autowired
    private WalertMapper walertMapper;
    
    @Autowired
    private TwilioSmsService twilioSmsService;
    
    @Autowired
    private WcontactService wcontactService;
    
    @Autowired
    private WuserService wuserService;
    
    @Value("${alert.auto-close-seconds:30}")
    private long alertAutoCloseSeconds;

    @Value("${alert.auto-close-check-ms:5000}")
    private long alertAutoCloseCheckMs;
    
    public Walert save(Walert walert){
        return walertRepository.save(walert);
    }

    /**
     * Crear una nueva alerta con ciclo de vida completo y notificar contactos por SMS
     * @param walert La alerta a crear (DTO del dominio)
     * @return La alerta guardada con todos los campos de ciclo de vida configurados
     */
    public Walert createAlert(Walert walert) {
        // Convertir DTO del dominio a entidad JPA
        Walerta walerta = walertMapper.toWalerta(walert);

        // Configurar explícitamente los campos de ciclo de vida
        walerta.setStatus(AlertStatus.ACTIVE);

        LocalDateTime now = LocalDateTime.now();
        walerta.setActivatedAt(now);
        walerta.setExpiresAt(now.plusSeconds(alertAutoCloseSeconds));

        // Valores por defecto para evitar nulls en respuesta
        if (walerta.getTimestamp() == null || walerta.getTimestamp().isBlank()) {
            walerta.setTimestamp(now.toString());
        }
        if (walerta.getEmergencyMode() == null) {
            walerta.setEmergencyMode(Boolean.FALSE);
        }

        int contactsCount = wcontactService.getByWuserId(walerta.getUserId())
                .map(List::size)
                .orElse(0);
        walerta.setContactosNotificados(contactsCount);

        // No establecer closedAt ni closeReason en la creación
        walerta.setClosedAt(null);
        walerta.setCloseReason(null);

        Walerta savedWalerta = walertaCrudRepository.save(walerta);
        Walert createdAlert = walertMapper.toWalert(savedWalerta);

        notifyEmergencyContacts(createdAlert);
        return createdAlert;
    }
    
    /**
     * Notifica a los contactos de emergencia del usuario por SMS
     * @param walert Alerta creada
     */
    private void notifyEmergencyContacts(Walert walert) {
        // Ejecutar en un thread separado para no bloquear
        new Thread(() -> {
            try {
                // Verificar si Twilio está configurado
                if (!twilioSmsService.isConfigured()) {
                    System.out.println("⚠ Twilio no configurado. SMS no enviados.");
                    return;
                }
                
                // Obtener información del usuario
                Optional<Wuser> userOpt = wuserService.getById(walert.getUserId());
                if (userOpt.isEmpty()) {
                    System.err.println("✗ Usuario no encontrado: " + walert.getUserId());
                    return;
                }
                Wuser user = userOpt.get();
                
                // Obtener contactos de emergencia
                Optional<List<Wcontact>> contactsOpt = wcontactService.getByWuserId(walert.getUserId());
                if (contactsOpt.isEmpty() || contactsOpt.get().isEmpty()) {
                    System.out.println("ℹ Usuario " + user.getName() + " no tiene contactos de emergencia.");
                    return;
                }
                
                List<Wcontact> contacts = contactsOpt.get();
                int sentCount = 0;
                
                // Enviar SMS a cada contacto
                for (Wcontact contact : contacts) {
                    String phone = contact.getPhone();
                    
                    // Validar formato de teléfono (debe incluir código de país)
                    if (phone == null || phone.isEmpty()) {
                        System.out.println("⚠ Contacto " + contact.getName() + " sin teléfono.");
                        continue;
                    }
                    
                    // Asegurar que tenga el prefijo +
                    if (!phone.startsWith("+")) {
                        System.out.println("⚠ Teléfono de " + contact.getName() + " sin código de país: " + phone);
                        continue;
                    }
                    
                    // Enviar SMS
                    String sid = twilioSmsService.sendEmergencyAlert(
                        phone, 
                        user.getName(), 
                        walert.getLatitud(), 
                        walert.getLongitud()
                    );
                    
                    if (sid != null) {
                        sentCount++;
                    }
                }
                
                System.out.println("✓ SMS enviados: " + sentCount + "/" + contacts.size());
                
            } catch (Exception e) {
                System.err.println("✗ Error notificando contactos: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();  // Ejecutar en background
    }

    /**
     * Cierra automaticamente alertas ACTIVE cuyo expiresAt ya paso.
     * Para pruebas locales se puede usar alert.auto-close-seconds=30.
     */
    @Scheduled(fixedDelayString = "${alert.auto-close-check-ms:5000}")
    public void autoCloseExpiredAlerts() {
        LocalDateTime now = LocalDateTime.now();
        List<Walerta> expiredActiveAlerts = walertaCrudRepository
                .findByStatusAndExpiresAtBefore(AlertStatus.ACTIVE, now);

        if (expiredActiveAlerts.isEmpty()) {
            return;
        }

        for (Walerta alert : expiredActiveAlerts) {
            alert.setStatus(AlertStatus.CLOSED);
            alert.setClosedAt(now);
            alert.setCloseReason(CloseReason.EXPIRED);
            walertaCrudRepository.save(alert);
        }
    }

    /**
     * Cierra manualmente una alerta activa
     * @param alertId ID de la alerta a cerrar
     * @return La alerta cerrada
     * @throws EntityNotFoundException si la alerta no existe
     * @throws IllegalStateException si la alerta no está ACTIVE
     */
    public Walert closeAlert(Integer alertId) {
        // Buscar la alerta por ID
        Walerta walerta = walertaCrudRepository.findById(alertId)
                .orElseThrow(() -> new EntityNotFoundException("Alerta con ID " + alertId + " no encontrada"));

        // Verificar que el status sea ACTIVE
        if (!walerta.getStatus().equals(AlertStatus.ACTIVE)) {
            throw new IllegalStateException("La alerta ya está cerrada. No se puede cerrar nuevamente");
        }

        // Usar una única instancia de LocalDateTime
        LocalDateTime now = LocalDateTime.now();

        // Establecer los campos de cierre
        walerta.setStatus(AlertStatus.CLOSED);
        walerta.setClosedAt(now);
        walerta.setCloseReason(CloseReason.USER);

        // Guardar cambios y convertir a DTO
        Walerta closedWalerta = walertaCrudRepository.save(walerta);
        return walertMapper.toWalert(closedWalerta);
    }

    public List<Walert> getAll() {
        // Suponiendo que walertRepository tiene un método getAll()
        return walertRepository.getAll();
    }

    public Optional<Walert> getById(Integer id) {
        // Suponiendo que walertRepository tiene un método getById()
        return walertRepository.getById(id);
    }
}