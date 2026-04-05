package com.ogs.wprotect.domain.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.dto.Login;
import com.ogs.wprotect.domain.dto.LoginResponse;
import com.ogs.wprotect.domain.repository.WuserRepository;

@Service
public class WuserService {
    @Autowired
    private WuserRepository wuserRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Wuser> getAll(){
        return wuserRepository.getAll();
    }

    public Wuser getByEmail(String email){
        return wuserRepository.getByEmail(email);
    }

    public Optional<Wuser> getById(Integer id){
        return wuserRepository.getById(id);
    }

    public Wuser save(Wuser wuser){
        // Hash password before saving
        if (wuser.getPassword() != null && !wuser.getPassword().isBlank()) {
            wuser.setPassword(passwordEncoder.encode(wuser.getPassword()));
        }
        return wuserRepository.save(wuser);
    }

    public Wuser patchById(Integer id, String name, String email, String phone) {
        return wuserRepository.patchById(id, name, email, phone);
    }

    public Optional<Wuser> getByPhone(String phone){
        return wuserRepository.getByPhone(phone);
    }

    /**
     * Login logic: validate user by email and password
     */
    public LoginResponse login(Login login) {
        Wuser user = wuserRepository.getByEmail(login.getUsername());
        if (user != null && user.getPassword() != null &&
                passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            // Optionally, generate a token or return user info
            return new LoginResponse(user.getId(), user.getName(), user.getEmail(), null);
        }
        return null;
    }

    /**
     * Autentica un usuario validando:
     * ✅ Email debe existir en BD
     * ✅ Password debe coincidir con hash registrado
     * ✅ Usuario debe estar activo (active = true)
     * @param email Email del usuario (normalizado a minúsculas)
     * @param rawPassword Password sin encriptar
     * @return Usuario completo con id y deviceId si credenciales válidas y usuario activo, null si no
     */
    public Wuser authenticateUser(String email, String rawPassword) {
        if (email == null || email.isBlank() || rawPassword == null || rawPassword.isBlank()) {
            return null;
        }

        // Normalizar email a minúsculas
        String normalizedEmail = email.toLowerCase().trim();

        // ✅ Obtener usuario por email
        Wuser user = getByEmail(normalizedEmail);
        if (user == null) {
            return null;  // Email no existe
        }

        // ✅ Validar que usuario esté activo
        if (!user.isActive()) {
            return null;  // Usuario no está activo
        }

        // ✅ Validar password
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return null;  // Usuario sin contraseña registrada
        }

        // Comparar password con hash usando BCrypt
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return null;  // Password incorrecto
        }

        // ✅ Devolver usuario completo incluyendo id y deviceId
        return user;
    }
}