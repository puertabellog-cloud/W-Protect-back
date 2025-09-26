package com.ogs.wprotect.domain;

public class Wcontact {
    private Integer id;
    private String name;
    private String phone;
    private String alias;
    private Integer userId;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Wuser getWuser() {
        return wuser;
    }

    public void setWuser(Wuser wuser) {
        this.wuser = wuser;
    }
}
