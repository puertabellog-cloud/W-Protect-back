package com.ogs.wprotect.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.service.WuserService;

@RestController
@RequestMapping("/w/users")
public class WuserController {
    @Autowired
    private WuserService wuserService;
    @GetMapping("/email/{email}")
    public ResponseEntity<Wuser> getByEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(wuserService.getByEmail(email), HttpStatus.OK);
    }

    
    @PostMapping("/save")
    public ResponseEntity<Wuser> save(@RequestBody Wuser wuser) {
        System.out.println("hola");
        System.out.println(wuser.getName());
        return new ResponseEntity<>(wuserService.save(wuser), HttpStatus.OK);
    }
}