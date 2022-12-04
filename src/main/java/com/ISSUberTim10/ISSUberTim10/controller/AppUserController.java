package com.ISSUberTim10.ISSUberTim10.controller;

import com.ISSUberTim10.ISSUberTim10.domain.AppUser;
import com.ISSUberTim10.ISSUberTim10.domain.Ride;
import com.ISSUberTim10.ISSUberTim10.dto.LoginDTO;
import com.ISSUberTim10.ISSUberTim10.dto.TokenResponceDTO;
import com.ISSUberTim10.ISSUberTim10.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class AppUserController {

    @Autowired
    IAppUserService service;

    @Autowired
    IRideService rideService;

    @Autowired

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AppUser>> getAll() {
        Collection<AppUser> users = service.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }



    @PostMapping(value = "/user")
    public void createAll() {
        service.createAll();
    }

    @DeleteMapping(value = "/user")
    public void deleteAll() {
        service.deleteAll();
    }

    @GetMapping(value = "/user/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Ride>> getUsersRides(@PathVariable Long id,
                                                          Pageable page,
                                                          @RequestParam(required = false) String from,
                                                          @RequestParam(required = false) String to) {
        Page<Ride> rides = rideService.getByUser(id, page);
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TokenResponceDTO> login(@RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/user/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {
        return new ResponseEntity<>("User successfully blocked", HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/user/{id}/unblock")
    public ResponseEntity<String> unblockUser(@PathVariable Long id) {
        return new ResponseEntity<>("User successfully unblocked", HttpStatus.NO_CONTENT);
    }


}
