package com.ogs.wprotect.domain;

import java.time.LocalDateTime;

public class Walert {
    private Integer id;
    private String message;
    private String latitud;
    private String longitud;
    private String timestamp;
    private Integer userId;
    private Wuser wuser;
    private Integer contactsNotified;
    private Boolean emergencyMode;

    // Campos de ciclo de vida
    private String status;
    private LocalDateTime activatedAt;
    private LocalDateTime closedAt;
    private LocalDateTime expiresAt;
    private String closeReason;

    public Boolean getEmergencyMode() {
        return emergencyMode;
    }

    public void setEmergencyMode(Boolean emergencyMode) {
        this.emergencyMode = emergencyMode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Wuser getWuser() {
        return wuser;
    }

    public void setWuser(Wuser wuser) {
        this.wuser = wuser;
    }

    public Integer getContactsNotified() {
        return contactsNotified;
    }

    public void setContactsNotified(Integer contactsNotified) {
        this.contactsNotified = contactsNotified;
    }

    // Getters y Setters para campos de ciclo de vida
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(LocalDateTime activatedAt) {
        this.activatedAt = activatedAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }
}