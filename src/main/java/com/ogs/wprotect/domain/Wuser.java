package com.ogs.wprotect.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Wuser {
    private int id;
    private String name;
    private String email;
    @JsonAlias({"phoneNumber", "telefono"})
    private String phone;
    @JsonAlias({"role", "perfil"})
    private String profile;
    @JsonAlias({"isActive", "activo"})
    private boolean active;
    @JsonAlias({"isEmergencyMode", "modoEmergencia"})
    private boolean emergencyMode;
    private String deviceId;

    
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public boolean isEmergencyMode() {
        return emergencyMode;
    }

    public void setEmergencyMode(boolean emergencyMode) {
        this.emergencyMode = emergencyMode;
    }
}