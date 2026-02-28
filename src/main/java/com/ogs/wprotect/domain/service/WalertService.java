package com.ogs.wprotect.domain.service;

import com.ogs.wprotect.domain.Walert;
import com.ogs.wprotect.domain.repository.WalertRepository;
import com.ogs.wprotect.persistence.entity.Walerta;
import com.ogs.wprotect.persistence.entity.AlertStatus;
import com.ogs.wprotect.persistence.entity.CloseReason;
import com.ogs.wprotect.persistence.mapper.WalertMapper;
import com.ogs.wprotect.persistence.crud.WalertaCrudRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WalertService {
    @Autowired
    private WalertRepository walertRepository;
    
    @Autowired
    private WalertaCrudRepository walertaCrudRepository;
    
    @Autowired
    private WalertMapper walertMapper;
    
    public Walert save(Walert walert){
        return walertRepository.save(walert);
    }

    /**
     * Crear una nueva alerta con ciclo de vida completo
     * @param walert La alerta a crear (DTO del dominio)
     * @return La alerta guardada con todos los campos de ciclo de vida configurados
     */
    public Walert createAlert(Walert walert) {
        // Convertir DTO del dominio a entidad JPA
        Walerta walerta = walertMapper.toWalerta(walert);
        
        // Configurar explícitamente los campos de ciclo de vida
        walerta.setStatus(AlertStatus.ACTIVE);
        
        // Usar una única instancia de LocalDateTime para evitar micro-diferencias de milisegundos
        LocalDateTime now = LocalDateTime.now();
        walerta.setActivatedAt(now);
        walerta.setExpiresAt(now.plusMinutes(60));
        
        // No establecer closedAt ni closeReason en la creación
        walerta.setClosedAt(null);
        walerta.setCloseReason(null);
        
        // Guardar en BD y convertir de vuelta a DTO del dominio
        Walerta savedWalerta = walertaCrudRepository.save(walerta);
        return walertMapper.toWalert(savedWalerta);
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
}
