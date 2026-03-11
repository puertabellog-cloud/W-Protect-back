package com.ogs.wprotect.domain;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Wcontact {
    private Integer id;
    private String name;
    @JsonAlias({"phoneNumber", "telefono"})
    private String phone;
    @JsonAlias({"userId", "usuarioId", "wuserId"})
    private Integer wusuarioId;
    private Wuser wuser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getWusuarioId() {
        return wusuarioId;
    }

    public void setWusuarioId(Integer userId) {
        this.wusuarioId = userId;
    }

    public Wuser getWuser() {
        return wuser;
    }

    public void setWuser(Wuser wuser) {
        this.wuser = wuser;
    }
}