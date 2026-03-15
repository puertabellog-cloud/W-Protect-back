package com.ogs.wprotect.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @com.ogs.wprotect.web.security.RequireAdmin
    @GetMapping("")
    public ResponseEntity<List<Wuser>> getAll() {
        return new ResponseEntity<>(wuserService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Wuser> getByEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(wuserService.getByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Wuser> save(@RequestBody Wuser wuser) {
        return new ResponseEntity<>(wuserService.save(wuser), HttpStatus.OK);
    }

    /**
     * Actualiza parcialmente el perfil de un usuario existente.
     * Solo permite modificar nombre, email y teléfono.
     * Requiere headers X-User-Id y X-Device-Id (validados por DeviceIdInterceptor).
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Wuser> patch(@PathVariable Integer id, @RequestBody Wuser patchData) {
        try {
            Wuser updated = wuserService.patchById(id, patchData.getName(), patchData.getEmail(), patchData.getPhone());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}