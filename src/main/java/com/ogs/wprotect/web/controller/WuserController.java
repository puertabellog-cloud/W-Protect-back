package com.ogs.wprotect.web.controller;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.service.WuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/w/users")
public class WuserController {
    @Autowired
    private WuserService wuserService;
    @GetMapping("/email/{email}")
    public ResponseEntity<Wuser> getByEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(wuserService.getByEmail(email), HttpStatus.OK);
    }
    @PutMapping("/save")
    public ResponseEntity<Wuser> save(@RequestBody Wuser wuser) {
        System.out.println("hola");
        System.out.println(wuser.getName());
        return new ResponseEntity<>(wuserService.save(wuser), HttpStatus.OK);
    }
    @GetMapping("device/{id}")
    public ResponseEntity<Optional<Wuser>> getByDeviceId(@PathVariable("id") String deviceId){
        System.out.println("el tema del tiempo");
        return new ResponseEntity<>(wuserService.getByDeviceId(deviceId), HttpStatus.OK);
    }
}
