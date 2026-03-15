package com.ogs.wprotect.web.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ogs.wprotect.domain.Walert;
import com.ogs.wprotect.domain.service.WalertService;
import com.ogs.wprotect.domain.service.WuserService;
import com.ogs.wprotect.web.security.RequireAdmin;

@RestController
@RequestMapping("/w/alerts")
public class WalertController {
    @Autowired
    private WalertService walertService;
    private WuserService wuserService;

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

    /**
     * Endpoint ADMIN: Listar todas las alertas agrupadas por usuario
    */
    @RequireAdmin
    @GetMapping("/all")
    public ResponseEntity<?> getAllAlertsGroupedByUser() {
        // Obtener todos los usuarios
        var users = wuserService.getAll();
        // Obtener todas las alertas (requiere método getAll en WalertService)
        var allAlerts = walertService.getAll();
        // Agrupar por usuario
        var result = users.stream().collect(Collectors.toMap(
            user -> user.getName(),
            user -> {
                var userAlerts = allAlerts.stream()
                        .filter(alert -> alert.getUserId() != null && alert.getUserId().equals(user.getId()))
                        .collect(Collectors.toList());
                return Map.of(
                    "deviceId", user.getDeviceId(),
                    "alerts", userAlerts
                );
            }
        ));
        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint ADMIN: Obtener detalle de una alerta específica (con info de usuario)
     */
    @RequireAdmin
    @GetMapping("/detail/{alertId}")
    public ResponseEntity<?> getAlertDetail(@PathVariable Integer alertId) {
        var alertOpt = walertService.getById(alertId);
        if (alertOpt.isEmpty()) return ResponseEntity.notFound().build();
        var alert = alertOpt.get();
        var userOpt = wuserService.getById(alert.getUserId());
        return ResponseEntity.ok(Map.of(
            "alert", alert,
            "user", userOpt.orElse(null)
        ));
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