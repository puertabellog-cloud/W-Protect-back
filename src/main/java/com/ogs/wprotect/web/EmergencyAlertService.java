/*
package com.ogs.oikoom.web;

import com.ogs.oikoom.domain.Wcontact;
import com.ogs.oikoom.domain.Wuser;
import com.ogs.oikoom.domain.repository.WcontactRepository;
import com.ogs.oikoom.domain.repository.WuserRepository;
import com.ogs.oikoom.persistence.WusuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmergencyAlertService {

    @Autowired
    private EmergencyAlertRepository emergencyAlertRepository;

    @Autowired
    private WcontactRepository emergencyContactRepository;

    @Autowired
    private WusuarioRepository userRepository;

    /**
     * Crear una nueva alerta de emergencia

    public EmergencyAlert createAlert(EmergencyAlert request) {
        try {
            // Validar que el usuario existe
            Wuser user = userRepository.getById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Crear la entidad de alerta
            EmergencyAlert alert = new EmergencyAlert();
            alert.setMessage(request.getMessage());
            alert.setLatitude(request.getLatitude());
            alert.setLongitude(request.getLongitude());
            alert.setAccuracy(request.getAccuracy());
            alert.setUserId(request.getUserId());
            alert.setTimestamp(LocalDateTime.now());
            alert.setSeen(false);

            // Contar cuántos contactos de emergencia tiene el usuario
            Optional<List<Wcontact>> contacts = emergencyContactRepository.getByWuserId(request.getUserId());
            alert.setContactsNotified(contacts.map(List::size).orElse(0));

            // Guardar en base de datos
            EmergencyAlert savedAlert = emergencyAlertRepository.save(alert);

            // Convertir a DTO de respuesta
            return convertToResponse(savedAlert, user);

        } catch (Exception e) {
            throw new RuntimeException("Error creando alerta de emergencia: " + e.getMessage(), e);
        }
    }

    /**
     * Obtener IDs de usuarios que son contactos de emergencia del usuario dado

    public List<Integer> getEmergencyContactUserIds(Integer userId) {
        try {
            // Buscar todos los contactos de emergencia del usuario
            Optional<List<Wcontact>> contacts = emergencyContactRepository.getByWuserId(userId);

            List<Integer> contactUserIds = new ArrayList<>();

            for (Wcontact contact : contacts.orElseGet(ArrayList::new)) {
                // Buscar usuario por teléfono (asumiendo que el teléfono es único)
                Optional<Wuser> contactUser = userRepository.getByPhone(contact.getPhone());

                if (contactUser.isPresent()) {
                    contactUserIds.add(contactUser.get().getId());
                } else {
                    // Si el contacto no tiene cuenta en la app, podrías:
                    // 1. Enviar SMS
                    // 2. Enviar WhatsApp
                    // 3. Registrar en log para seguimiento
                    System.out.println("Contacto " + contact.getPhone() +
                            " no tiene cuenta en la app. Considerar envío por SMS/WhatsApp");
                }
            }

            return contactUserIds;

        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo contactos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtener alertas donde el usuario es contacto de emergencia (alertas que debe recibir)

    public List<EmergencyAlert> getAlertsForContact(Integer contactUserId) {
        try {
            // Buscar el usuario contacto
            Optional<Wuser> contactUser = userRepository.getById(contactUserId);

            // Buscar alertas donde este usuario es contacto de emergencia
            List<EmergencyAlert> alerts = emergencyAlertRepository.findAlertsForContact(contactUser.getPhone());

            return alerts.stream()
                    .map(this::convertToResponseWithSender)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo alertas para contacto: " + e.getMessage(), e);
        }
    }

    /**
     * Marcar una alerta como vista

    public void markAlertAsSeen(Integer alertId) {
        try {
            EmergencyAlert alert = emergencyAlertRepository.findById(alertId)
                    .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));

            alert.setSeen(true);
            alert.setSeenAt(LocalDateTime.now());

            emergencyAlertRepository.save(alert);

        } catch (Exception e) {
            throw new RuntimeException("Error marcando alerta como vista: " + e.getMessage(), e);
        }
    }

    /**
     * Obtener historial de alertas enviadas por un usuario

    public List<EmergencyAlert> getUserAlerts(Integer userId) {
        try {
            List<EmergencyAlert> alerts = emergencyAlertRepository.findByUserIdOrderByTimestampDesc(userId);

            Wuser user = userRepository.getById(userId);

            return alerts.stream()
                    .map(alert -> convertToResponse(alert, user))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo alertas del usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Confirmar que una alerta fue recibida por un contacto

    public void confirmAlertReceived(Integer alertId, Integer contactUserId) {
        try {
            // Podrías crear una tabla de confirmaciones si quieres rastrear esto
            // Por ahora solo lo registramos en log
            System.out.println("Alerta " + alertId + " confirmada como recibida por usuario " + contactUserId);

            // TODO: Implementar tabla de confirmaciones si es necesario

        } catch (Exception e) {
            throw new RuntimeException("Error confirmando recepción: " + e.getMessage(), e);
        }
    }

    /**
     * Convertir entidad a DTO de respuesta

    private EmergencyAlert convertToResponse(EmergencyAlert alert, Wuser sender) {
        EmergencyAlert response = new EmergencyAlert();
        response.setId(alert.getId());
        response.setMessage(alert.getMessage());
        response.setLatitude(alert.getLatitude());
        response.setLongitude(alert.getLongitude());
        response.setTimestamp(LocalDateTime.parse(alert.getTimestamp().toString()));
        response.setUserId(alert.getUserId());
        response.setContactsNotified(alert.getContactsNotified());
        response.setSeen(alert.getSeen());

        if (sender != null) {
            response.setSenderName(sender.getName());
            response.setSenderPhone(sender.getPhone());
        }

        return response;
    }

    /**
     * Convertir entidad a DTO incluyendo información del remitente

    private EmergencyAlert convertToResponseWithSender(EmergencyAlert alert) {
        Wuser sender = userRepository.getById(alert.getUserId());
        return convertToResponse(alert, sender);
    }
}
*/