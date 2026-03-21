package com.ogs.wprotect.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ogs.wprotect.domain.dto.Login;
import com.ogs.wprotect.domain.dto.LoginResponse;
import com.ogs.wprotect.domain.service.WuserService;


@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private WuserService wuserService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Login login) {
        LoginResponse response = wuserService.login(login);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
