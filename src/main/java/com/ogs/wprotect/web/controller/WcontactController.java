package com.ogs.wprotect.web.controller;

import com.ogs.wprotect.domain.Wcontact;
import com.ogs.wprotect.domain.service.WcontactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/w/contacts")
public class WcontactController {
    @Autowired
    private WcontactService wcontactService;
    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<List<Wcontact>>> getByWuserId(@PathVariable("userId") Integer wuserId){
        return new ResponseEntity<>(wcontactService.getByWuserId(wuserId), HttpStatus.OK);
    }
    @PutMapping("/save")
    public ResponseEntity<Wcontact> save(@RequestBody Wcontact wcontact){
        System.out.println("replay");
        System.out.println(wcontact.toString());
        return new ResponseEntity<>(wcontactService.save(wcontact), HttpStatus.OK);
    }
    @DeleteMapping("delete")
    public ResponseEntity<Wcontact> delete(@PathVariable("contactId") Integer id){
        return new ResponseEntity<>(wcontactService.delete(id), HttpStatus.OK);
    }
}
