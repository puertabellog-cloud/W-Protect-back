package com.ogs.wprotect.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class AdminAlertGroupDto {
    private String deviceId;
    private List<AdminAlertDto> alerts;

    public AdminAlertGroupDto() {
        this.deviceId = "";
        this.alerts = new ArrayList<>();
    }

    public AdminAlertGroupDto(String deviceId, List<AdminAlertDto> alerts) {
        this.deviceId = deviceId == null ? "" : deviceId;
        this.alerts = alerts == null ? new ArrayList<>() : alerts;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? "" : deviceId;
    }

    public List<AdminAlertDto> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<AdminAlertDto> alerts) {
        this.alerts = alerts == null ? new ArrayList<>() : alerts;
    }
}
