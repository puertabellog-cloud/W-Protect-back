package com.ogs.wprotect.domain.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogs.wprotect.domain.dto.LocationRequest;
import com.ogs.wprotect.domain.repository.WubicacionRepository;
import com.ogs.wprotect.persistence.crud.WalertaCrudRepository;
import com.ogs.wprotect.persistence.entity.AlertStatus;
import com.ogs.wprotect.persistence.entity.Walerta;
import com.ogs.wprotect.persistence.entity.Wubicacion;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WubicacionService {

    @Autowired
    private WubicacionRepository wubicacionRepository;

    @Autowired
    private WalertaCrudRepository walertaCrudRepository;

    public Wubicacion trackLocation(Integer alertId, LocationRequest locationRequest) {
        if (alertId == null || locationRequest == null) {
            throw new IllegalStateException("ID de alerta y datos de ubicación son obligatorios");
        }
        if (locationRequest.getLatitud() == null || locationRequest.getLongitud() == null) {
            throw new IllegalStateException("Latitud y longitud son obligatorias");
        }

        Walerta alert = walertaCrudRepository.findById(alertId)
                .orElseThrow(() -> new EntityNotFoundException("Alerta con ID " + alertId + " no encontrada"));

        if (!AlertStatus.ACTIVE.equals(alert.getStatus())) {
            throw new IllegalStateException("Solo se pueden registrar ubicaciones en alertas ACTIVE");
        }

        LocalDateTime now = LocalDateTime.now();

        Wubicacion ubicacion = new Wubicacion();
        ubicacion.setLatitud(locationRequest.getLatitud());
        ubicacion.setLongitud(locationRequest.getLongitud());
        ubicacion.setAccuracy(locationRequest.getAccuracy());
        ubicacion.setTimestamp(now);
        ubicacion.setCreatedAt(now);
        ubicacion.setAlerta(alert);

        return wubicacionRepository.save(ubicacion);
    }
}