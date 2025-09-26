package com.ogs.wprotect.domain.dto;

public class LoginResponse {
    private Integer id;
    private String name;
    private String email;
    private String token; // opcional

    public LoginResponse(Integer id, String name, String email, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.token = token;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}