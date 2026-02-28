package com.ogs.wprotect.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "walerta")
public class Walerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String mensaje;
    private String latitud;
    private String longitud;
    private String timestamp;
    @Column(name = "user_id")
    private Integer userId;
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Wusuario wusuario;
    private Integer contactosNotificados;

    // Campos de ciclo de vida
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertStatus status = AlertStatus.ACTIVE;

    @Column(nullable = false)
    private LocalDateTime activatedAt;

    private LocalDateTime closedAt;

    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    private CloseReason closeReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    public Wusuario getWusuario() {
        return wusuario;
    }

    public void setWusuario(Wusuario wusuario) {
        this.wusuario = wusuario;
    }

    public Integer getContactosNotificados() {
        return contactosNotificados;
    }

    public void setContactosNotificados(Integer contactosNotificados) {
        this.contactosNotificados = contactosNotificados;
    }

    // Getters y Setters para campos de ciclo de vida
    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
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

    public CloseReason getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(CloseReason closeReason) {
        this.closeReason = closeReason;
    }
}
