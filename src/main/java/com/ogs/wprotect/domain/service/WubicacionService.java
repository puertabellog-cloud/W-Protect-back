package com.ogs.wprotect.domain.service;

import org.springframework.stereotype.Service;

import com.ogs.wprotect.domain.dto.LocationRequest;
import com.ogs.wprotect.persistence.entity.Wubicacion;

@Service
public class WubicacionService {
    // Implementa la lógica de servicio real aquí

    /**
     * método de ejemplo para que el controlador funcione.
     * La implementación real deberá validar, guardar en un repositorio, etc.
     */
    public Wubicacion trackLocation(Integer alertId, LocationRequest locationRequest) {
        if (alertId == null || locationRequest == null) {
            throw new IllegalStateException("ID de alerta y datos de ubicación son obligatorios");
        }

        // crea un objeto de prueba; sustituir por la lógica de negocio
        Wubicacion ubicacion = new Wubicacion();

        // supongo que LocationRequest tiene getters de lat/lng/fecha
        ubicacion.setLatitud(locationRequest.getLatitud());
        ubicacion.setLongitud(locationRequest.getLongitud());

        // normalmente aquí harías wubicacionRepository.save(ubicacion);
        return ubicacion;
    }
}
