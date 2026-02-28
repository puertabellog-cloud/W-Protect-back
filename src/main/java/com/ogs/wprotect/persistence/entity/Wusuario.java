package com.ogs.wprotect.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="wusuario")
public class Wusuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer usuarioId;
    private String nombre;
    private String email;
    private String perfil;
    private String telefono;
    private Boolean activo;
    @OneToMany(mappedBy = "wusuario")
    private List<Wcontacto> wcontactos;
    @OneToMany(mappedBy = "wusuario")
    private List<Walerta> walertas;

    public Boolean getEmergencyMode() {
        return emergencyMode;
    }

    public void setEmergencyMode(Boolean emergencyMode) {
        this.emergencyMode = emergencyMode;
    }

    @Column(name = "emergency_mode", columnDefinition = "boolean default false", nullable = false)
    private Boolean emergencyMode;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<Wcontacto> getWcontactos() {
        return wcontactos;
    }

    public void setWcontactos(List<Wcontacto> wcontactos) {
        this.wcontactos = wcontactos;
    }

    public List<Walerta> getWalertas() {
        return walertas;
    }

    public void setWalertas(List<Walerta> walertas) {
        this.walertas = walertas;
    }
}