package com.ogs.wprotect.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wcontact")
public class Wcontacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String nombre;
    private String telefono;
    private String alias;
    @Column(name = "wusuario_id")
    private Integer wusuarioId;
    @ManyToOne
    @JoinColumn(name = "wusuario_id", insertable = false, updatable = false)
    private Wusuario wusuario;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getWusuarioId() {
        return wusuarioId;
    }

    public void setWusuarioId(Integer wusuarioId) {
        this.wusuarioId = wusuarioId;
    }

    public Wusuario getWusuario() {
        return wusuario;
    }

    public void setWusuario(Wusuario wusuario) {
        this.wusuario = wusuario;
    }
}
