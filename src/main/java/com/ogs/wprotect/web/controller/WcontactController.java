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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ogs.wprotect.domain.Wcontact;
import com.ogs.wprotect.domain.service.WcontactService;

@RestController
@RequestMapping("/w/contacts")
public class WcontactController {
    private static final Logger logger = LoggerFactory.getLogger(WcontactController.class);

    @Autowired
    private WcontactService wcontactService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<List<Wcontact>>> getByWuserId(@PathVariable("userId") Integer wuserId){
        return new ResponseEntity<>(wcontactService.getByWuserId(wuserId), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Wcontact> save(@RequestBody Wcontact wcontact){
        logger.info("Creating new contact");
        return new ResponseEntity<>(wcontactService.save(wcontact), HttpStatus.CREATED);
    }

    @PutMapping("/save/{id}")
    public ResponseEntity<Wcontact> update(@PathVariable("id") Integer id, @RequestBody Wcontact wcontact) {
        logger.info("Updating contact with ID: {}", id);
        wcontact.setId(id);
        return new ResponseEntity<>(wcontactService.save(wcontact), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        logger.info("Deleting contact with ID: {}", id);
        wcontactService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}