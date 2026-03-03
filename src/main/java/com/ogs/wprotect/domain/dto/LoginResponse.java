package com.ogs.wprotect.domain.dto;

public class LoginResponse {
    private Integer id;
    private String name;
    private String email;
    private String token; // opcional
    //Nota: el token puede ser null por lo cual es recomendable usar Nullable,sino es null y
    //es un valor o caracter vacio,se recomineda Optional<> ya que puede generar confusion en la logica de negocio

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