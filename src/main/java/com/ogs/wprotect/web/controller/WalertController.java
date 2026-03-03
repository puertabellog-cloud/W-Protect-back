package com.ogs.wprotect.web.controller;

import com.ogs.wprotect.domain.Walert;
import com.ogs.wprotect.domain.service.WalertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/w/alerts")
public class WalertController {
    @Autowired
    private WalertService walertService;

    /**
     * Crea una nueva alerta con ciclo de vida completo
     * @param walert Datos de la alerta a crear
     * @return 201 CREATED con la alerta creada incluyendo id y campos de ciclo de vida
     */
    @PostMapping
    public ResponseEntity<Walert> createAlert(@RequestBody Walert walert) {
        Walert createdAlert = walertService.createAlert(walert);
        return new ResponseEntity<>(createdAlert, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Walert> closeAlert(@PathVariable Integer id) {
        try {
            Walert closedAlert = walertService.closeAlert(id);
            return new ResponseEntity<>(closedAlert, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
/*
@Autowired
    private EmergencyAlertService emergencyAlertService;

    @Autowired
    private EmergencyWebSocketHandler webSocketHandler;

    @PostMapping
    public ResponseEntity<EmergencyAlertResponse> sendEmergencyAlert(
            @RequestBody EmergencyAlertRequest request) {

        try {
            // Guardar en base de datos
            EmergencyAlertResponse alert = emergencyAlertService.createAlert(request);

            // Obtener contactos de emergencia del usuario
            List<Integer> contactUserIds = emergencyAlertService.getEmergencyContactUserIds(request.getUserId());

            // Enviar via WebSocket a contactos conectados
            webSocketHandler.sendEmergencyAlert(contactUserIds, alert);

            return ResponseEntity.ok(alert);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
 */