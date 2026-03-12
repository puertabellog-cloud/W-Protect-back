package com.ogs.wprotect.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class LocationRequest {
    @JsonAlias({"latitude", "lat"})
    private Double latitud;
    @JsonAlias({"longitude", "lng"})
    private Double longitud;
    @JsonAlias({"accuracy", "precission", "precision"})
    private Double accuracy;
    @JsonAlias({"message", "mensaje"})
    private String mensaje;

    public LocationRequest() {}

    public LocationRequest(Double latitud, Double longitud, String mensaje) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.mensaje = mensaje;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}