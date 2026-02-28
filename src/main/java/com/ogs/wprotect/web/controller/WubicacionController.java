package com.ogs.wprotect.web.controller;

import com.ogs.wprotect.domain.dto.LocationRequest;
import com.ogs.wprotect.domain.service.WubicacionService;
import com.ogs.wprotect.persistence.entity.Wubicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/w/alerts")
public class WubicacionController {

    @Autowired
    private WubicacionService wubicacionService;

    /**
     * Registra una nueva ubicación para una alerta activa
     * @param alertId ID de la alerta
     * @param locationRequest Datos de ubicación
     * @return 201 CREATED con la ubicación guardada
     */
    @PostMapping("/{alertId}/locations")
    public ResponseEntity<Wubicacion> trackLocation(
            @PathVariable Integer alertId,
            @RequestBody LocationRequest locationRequest) {
        try {
            Wubicacion ubicacion = wubicacionService.trackLocation(alertId, locationRequest);
            return new ResponseEntity<>(ubicacion, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
