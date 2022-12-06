package com.ISSUberTim10.ISSUberTim10.controller;

import com.ISSUberTim10.ISSUberTim10.domain.AppUser;
import com.ISSUberTim10.ISSUberTim10.domain.Ride;
import com.ISSUberTim10.ISSUberTim10.dto.*;
import com.ISSUberTim10.ISSUberTim10.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class AppUserController {

    @Autowired
    IAppUserService service;

    @Autowired
    IRideService rideService;

    @Autowired

    @GetMapping(value = "/user", produces = "application/json")
    public ResponseEntity<Collection<AppUser>> getAll() {
        Collection<AppUser> users = service.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/user2", produces = "application/json")
    public ResponseEntity<AllUsersDTO> getAll(Pageable page) {
        Page<AppUser> users = service.getAll(page);
        ArrayList<UserExpandedDTO> usersDTO = new ArrayList<>();
        usersDTO.add(new UserExpandedDTO());
        return new ResponseEntity<>(new AllUsersDTO(usersDTO.size(), usersDTO), HttpStatus.OK);
    }



    @PostMapping(value = "/user")
    public void createAll() {
        service.createAll();
    }

    @DeleteMapping(value = "/user")
    public void deleteAll() {
        service.deleteAll();
    }

    @GetMapping(value = "/user/{id}/ride", produces = "application/json")
    public ResponseEntity<RideResponseDTO> getUsersRides(@PathVariable Long id,
                                                         Pageable page,
                                                         @RequestParam(required = false) String from,
                                                         @RequestParam(required = false) String to) {
        //Page<Ride> rides = rideService.getByUser(id, page);
        ArrayList<RideDTO> ridesDTO = new ArrayList<>();
        ArrayList<LocationDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new LocationDTO("", 10.0, 10.0));
        passengers.add(new UserDTO(1L, "", "VOZAC"));
        ridesDTO.add(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, "", "VOZAC"),
                passengers, 5, "", true, true));
        return new ResponseEntity<>(new RideResponseDTO(ridesDTO.size(), ridesDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TokenResponceDTO> login(@RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(new TokenResponceDTO(),HttpStatus.OK);
    }

    @PutMapping(value = "/user/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {
        return new ResponseEntity<>("User successfully blocked", HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/user/{id}/unblock")
    public ResponseEntity<String> unblockUser(@PathVariable Long id) {
        return new ResponseEntity<>("User successfully unblocked", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/user/{id}/message", produces = "application/json")
    public ResponseEntity<MessageResponseDTO> getMessagesById(@PathVariable Long id,
                                                              @RequestParam(required = false) int userId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/user/{id}/message", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MessageReceivedDTO> sendMessagesById(@PathVariable Long id,
                                                              @RequestBody MessageSentDTO messageSent) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
