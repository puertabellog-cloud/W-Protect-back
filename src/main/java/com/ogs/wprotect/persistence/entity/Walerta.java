package com.ogs.wprotect.persistence.entity;

import jakarta.persistence.*;

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
}
