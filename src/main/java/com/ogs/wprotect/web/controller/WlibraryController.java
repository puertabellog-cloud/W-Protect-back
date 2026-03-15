package com.ogs.wprotect.web.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(WlibraryController.class);

    @Autowired
    private WlibraryService wlibraryService;

    @GetMapping("")
    public ResponseEntity<List<WLibrary>> getAll() {
        log.info("GET /w/library called");
        List<WLibrary> libraries = wlibraryService.getAll();
        log.info("GET /w/library completed count={}", libraries.size());
        return new ResponseEntity<>(libraries, HttpStatus.OK);
    }

    @com.ogs.wprotect.web.security.RequireAdmin
    @GetMapping("/{id}")
    public ResponseEntity<WLibrary> getById(@PathVariable Integer id) {
        log.info("GET /w/library/{} called", id);
        Optional<WLibrary> library = wlibraryService.getById(id);
        if (library.isPresent()) {
            log.info("GET /w/library/{} found", id);
            return new ResponseEntity<>(library.get(), HttpStatus.OK);
        }

        log.warn("GET /w/library/{} not found", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @com.ogs.wprotect.web.security.RequireAdmin
    @PostMapping("/save")
    public ResponseEntity<WLibrary> save(@RequestBody WLibrary wLibrary) {
        log.info("POST /w/library/save called name={} urlPresent={}",
                wLibrary.getName(),
                wLibrary.getUrl() != null && !wLibrary.getUrl().isBlank());
        WLibrary created = wlibraryService.save(wLibrary);
        log.info("POST /w/library/save created id={}", created.getId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @com.ogs.wprotect.web.security.RequireAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("DELETE /w/library/{} called", id);
        wlibraryService.deleteById(id);
        log.info("DELETE /w/library/{} completed", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @com.ogs.wprotect.web.security.RequireAdmin
    @PatchMapping("/edit/{id}")
    public ResponseEntity<WLibrary> patch(@PathVariable Integer id, @RequestBody WLibrary patchData) {
        log.info("PATCH /w/library/edit/{} called fields namePresent={} descriptionPresent={} urlPresent={}",
                id,
                patchData.getName() != null,
                patchData.getDescription() != null,
                patchData.getUrl() != null);
        WLibrary updated = wlibraryService.patchById(id, patchData.getName(), patchData.getDescription(), patchData.getUrl());
        log.info("PATCH /w/library/edit/{} completed", id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}