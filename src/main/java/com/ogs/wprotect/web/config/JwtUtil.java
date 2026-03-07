package com.ogs.wprotect.web.config;


import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtUtil {
    private final Algorithm algorithm;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String create(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuer("WPROTECT")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))//Se expira en 15 días
                .sign(algorithm);
    }
    public boolean isValid(String jwt){
        try {
            JWT.require(algorithm)
                    .build()
                    .verify(jwt);
            return true;
        } catch (JWTVerificationException e){
            return false;
        }
    }
    public String getUsername(String jwt){
        return JWT.require(algorithm)
                .build()
                .verify(jwt)
                .getSubject();
    }
}
