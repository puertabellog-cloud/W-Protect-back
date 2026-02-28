package com.ogs.wprotect.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wubicacion")
public class Wubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double latitud;
    private Double longitud;
    private LocalDateTime timestamp;
    private Double accuracy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Relación ManyToOne con Walerta
    @ManyToOne
    @JoinColumn(name = "alert_id", nullable = false)
    @JsonIgnore
    private Walerta alerta;

    // Constructores
    public Wubicacion() {}

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Double getAccuracy() { return accuracy; }
    public void setAccuracy(Double accuracy) { this.accuracy = accuracy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Walerta getAlerta() { return alerta; }
    public void setAlerta(Walerta alerta) { this.alerta = alerta; }
}
