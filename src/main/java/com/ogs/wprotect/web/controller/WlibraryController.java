package com.ogs.wprotect.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ogs.wprotect.domain.WLibrary;
import com.ogs.wprotect.domain.service.WlibraryService;

@RestController
@RequestMapping("/w/library")
public class WlibraryController {
    @Autowired
    private WlibraryService wlibraryService;

    @com.ogs.wprotect.web.security.RequireAdmin
    @GetMapping("")
    public ResponseEntity<List<WLibrary>> getAll() {
        return new ResponseEntity<>(wlibraryService.getAll(), HttpStatus.OK);
    }

    @com.ogs.wprotect.web.security.RequireAdmin
    @GetMapping("/{id}")
    public ResponseEntity<WLibrary> getById(@PathVariable Integer id) {
        Optional<WLibrary> library = wlibraryService.getById(id);
        return library.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @com.ogs.wprotect.web.security.RequireAdmin
    @PostMapping("/save")
    public ResponseEntity<WLibrary> save(@RequestBody WLibrary wLibrary) {
        return new ResponseEntity<>(wlibraryService.save(wLibrary), HttpStatus.CREATED);
    }

    @com.ogs.wprotect.web.security.RequireAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        wlibraryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @com.ogs.wprotect.web.security.RequireAdmin
    @PatchMapping("/edit/{id}")
    public ResponseEntity<WLibrary> patch(@PathVariable Integer id, @RequestBody WLibrary patchData) {
        WLibrary updated = wlibraryService.patchById(id, patchData.getName(), patchData.getDescription(), patchData.getUrl());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
