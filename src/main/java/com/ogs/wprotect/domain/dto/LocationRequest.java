package com.ogs.wprotect.domain.dto;

public class LocationRequest {
    private Double latitud;
    private Double longitud;
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
