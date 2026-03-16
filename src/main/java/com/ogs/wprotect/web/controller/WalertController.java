package com.ogs.wprotect.web.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.dto.AdminAlertDetailDto;
import com.ogs.wprotect.domain.dto.AdminAlertDto;
import com.ogs.wprotect.domain.dto.AdminAlertGroupDto;
import com.ogs.wprotect.domain.dto.AdminUserDto;
import com.ogs.wprotect.domain.service.WalertService;
import com.ogs.wprotect.domain.service.WuserService;
import com.ogs.wprotect.web.security.RequireAdmin;

@RestController
@RequestMapping("/w/alerts")
public class WalertController {
    @Autowired
    private WalertService walertService;

    @Autowired
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
     * Endpoint ADMIN: Listar alertas agrupadas por usuario.
     * Respuesta vacia: {}
     */
    @RequireAdmin
    @GetMapping("/all")
    public ResponseEntity<Map<String, AdminAlertGroupDto>> getAllAlertsGroupedByUser() {
        List<Wuser> users = Optional.ofNullable(wuserService.getAll()).orElseGet(ArrayList::new);
        List<Walert> allAlerts = Optional.ofNullable(walertService.getAll()).orElseGet(ArrayList::new);

        if (users.isEmpty() || allAlerts.isEmpty()) {
            return ResponseEntity.ok(new LinkedHashMap<>());
        }

        Map<String, AdminAlertGroupDto> result = new LinkedHashMap<>();

        for (Wuser user : users) {
            if (user == null) {
                continue;
            }

            List<AdminAlertDto> userAlerts = allAlerts.stream()
                    .filter(alert -> alert != null
                            && alert.getUserId() != null
                            && user.getId() == alert.getUserId())
                    .map(this::toAdminAlertDto)
                    .toList();

            String userKey = buildSafeUserKey(user, result);
            String deviceId = user.getDeviceId() == null ? "" : user.getDeviceId();
            result.put(userKey, new AdminAlertGroupDto(deviceId, userAlerts));
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint ADMIN: Detalle de alerta sin exponer entidades anidadas riesgosas.
     */
    @RequireAdmin
    @GetMapping("/detail/{alertId}")
    public ResponseEntity<AdminAlertDetailDto> getAlertDetail(@PathVariable Integer alertId) {
        var alertOpt = walertService.getById(alertId);
        if (alertOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Walert alert = alertOpt.get();
        Optional<Wuser> userOpt = alert.getUserId() == null
                ? Optional.empty()
                : wuserService.getById(alert.getUserId());

        AdminAlertDetailDto detail = new AdminAlertDetailDto();
        detail.setAlert(toAdminAlertDto(alert));
        detail.setUser(userOpt.map(this::toAdminUserDto).orElse(null));

        return ResponseEntity.ok(detail);
    }

    private String buildSafeUserKey(Wuser user, Map<String, AdminAlertGroupDto> existing) {
        String base = user.getName();
        if (base == null || base.isBlank()) {
            base = "user-" + user.getId();
        }

        if (!existing.containsKey(base)) {
            return base;
        }

        return base + "-" + user.getId();
    }

    private AdminAlertDto toAdminAlertDto(Walert alert) {
        AdminAlertDto dto = new AdminAlertDto();
        dto.setId(alert.getId());
        dto.setMessage(alert.getMessage());
        dto.setLatitud(alert.getLatitud());
        dto.setLongitud(alert.getLongitud());
        dto.setTimestamp(alert.getTimestamp());
        dto.setUserId(alert.getUserId());
        dto.setContactsNotified(alert.getContactsNotified());
        dto.setEmergencyMode(alert.getEmergencyMode());
        dto.setStatus(alert.getStatus());
        dto.setActivatedAt(alert.getActivatedAt());
        dto.setClosedAt(alert.getClosedAt());
        dto.setExpiresAt(alert.getExpiresAt());
        dto.setCloseReason(alert.getCloseReason());
        return dto;
    }

    private AdminUserDto toAdminUserDto(Wuser user) {
        AdminUserDto dto = new AdminUserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setProfile(user.getProfile());
        dto.setActive(user.isActive());
        dto.setEmergencyMode(user.isEmergencyMode());
        dto.setDeviceId(user.getDeviceId());
        return dto;
    }
}