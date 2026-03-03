package com.ogs.wprotect.domain.dto;

public class LocationRequest {
    private String latitud;
    private String longitud;
    private String mensaje;

    public LocationRequest() {}

    public LocationRequest(String latitud, String longitud, String mensaje) {
        this.latitud = latitud;
        this.longitud = longitud;
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
