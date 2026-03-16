package com.ogs.wprotect.domain.dto;

public class AdminAlertDetailDto {
    private AdminAlertDto alert;
    private AdminUserDto user;

    public AdminAlertDto getAlert() {
        return alert;
    }

    public void setAlert(AdminAlertDto alert) {
        this.alert = alert;
    }

    public AdminUserDto getUser() {
        return user;
    }

    public void setUser(AdminUserDto user) {
        this.user = user;
    }
}
