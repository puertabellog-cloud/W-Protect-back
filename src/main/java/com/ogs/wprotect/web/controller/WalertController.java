package com.ogs.wprotect.web.controller;

import com.ogs.wprotect.domain.Walert;
import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.service.WalertService;
import com.ogs.wprotect.domain.service.WuserService;
import com.ogs.wprotect.persistence.entity.Wusuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/w/alerts")
public class WalertController {
    @Autowired
    private WalertService walertService;
    @Autowired
    private WuserService wuserService;
    @PutMapping("/save")
    public ResponseEntity<Walert> save(@RequestBody Walert walert){
        Optional<Wuser> wuser = wuserService.getById(walert.getUserId());
        if(wuser.isPresent()){

            wuserService.save(wuser.get());
        }
        return new ResponseEntity<>(walertService.save(walert), HttpStatus.OK);
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