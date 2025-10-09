package com.ogs.wprotect.web.controller;

import com.ogs.wprotect.domain.LocationTrackingRequest;
import com.ogs.wprotect.domain.repository.WubicacionRepository;
import com.ogs.wprotect.persistence.entity.Wubicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/w/location")
public class WubicacionController {

    @Autowired
    private WubicacionRepository wubicacionRepository;

    @PostMapping("/track")
    public ResponseEntity<String> trackLocation(@RequestBody LocationTrackingRequest request) {
        try {
            // Crear nueva ubicación
            Wubicacion wubicacion = new Wubicacion();
            wubicacion.setDeviceId(request.getDeviceId());
            wubicacion.setLatitud(request.getLatitud());
            wubicacion.setLongitud(request.getLongitud());
            wubicacion.setTimestamp(request.getTimestamp());
            wubicacion.setAccuracy(request.getAccuracy());

            // Guardar en base de datos
            wubicacionRepository.save(wubicacion);

            System.out.println("📍 Ubicación guardada: Device " + request.getDeviceId() +
                    " - Lat: " + request.getLatitud() + ", Lng: " + request.getLongitud());

            return ResponseEntity.ok("Ubicación guardada correctamente");

        } catch (Exception e) {
            System.err.println("❌ Error guardando ubicación: " + e.getMessage());
            return ResponseEntity.status(500).body("Error guardando ubicación: " + e.getMessage());
        }
    }
}