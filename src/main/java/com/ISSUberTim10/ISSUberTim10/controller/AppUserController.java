package com.ISSUberTim10.ISSUberTim10.controller;

import com.ISSUberTim10.ISSUberTim10.domain.AppUser;
import com.ISSUberTim10.ISSUberTim10.dto.*;
import com.ISSUberTim10.ISSUberTim10.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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


    @GetMapping(value = "/user2", produces = "application/json")
    public ResponseEntity<Collection<AppUser>> getAll() {
        Collection<AppUser> users = service.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/user", produces = "application/json")
    public ResponseEntity<AllUsersDTO> getAll(Pageable page) {
        Page<AppUser> users = service.getAll(page);
        ArrayList<UserExpandedDTO> usersDTO = new ArrayList<>();
        usersDTO.add(new UserExpandedDTO(1L, "Marija", "Ivkov", "src", "05156", "marija@gmail", "Novi Sad"));
        usersDTO.add(new UserExpandedDTO(1L, "Marija", "Ivkov", "src", "05156", "marija@gmail", "Novi Sad"));
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
    public ResponseEntity<RideResponseDTO> getUsersRides(@PathVariable Integer id,
                                                         Pageable page,
                                                         @RequestParam(required = false) String sort,
                                                         @RequestParam(required = false) String from,
                                                         @RequestParam(required = false) String to) {
        //Page<Ride> rides = rideService.getByUser(id, page);
        ArrayList<RideDTO> ridesDTO = new ArrayList<>();
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        ridesDTO.add(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, null, new RejectionDTO("zato", "11.11.2022.")));
        return new ResponseEntity<>(new RideResponseDTO(ridesDTO.size(), ridesDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(new TokenResponseDTO("dasdsda", "dfsfsdfsdef"),HttpStatus.OK);
    }

    @PutMapping(value = "/user/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable Integer id) {
        return new ResponseEntity<>("User successfully blocked", HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/user/{id}/unblock")
    public ResponseEntity<String> unblockUser(@PathVariable Integer id) {
        return new ResponseEntity<>("User successfully unblocked", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/user/{id}/message", produces = "application/json")
    public ResponseEntity<MessageResponseDTO> getMessagesById(@PathVariable Integer id) {
        ArrayList<MessageReceivedDTO> messages = new ArrayList<>();
        messages.add(new MessageReceivedDTO(10L, "11.11.2022.", 1L, 2L, "Message", "RIDE", 3L));
        return new ResponseEntity<>(new MessageResponseDTO(messages.size(), messages), HttpStatus.OK);
    }

    @PostMapping(value = "/user/{id}/message", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MessageReceivedDTO> sendMessagesById(@PathVariable Integer id,
                                                              @RequestBody MessageSentDTO messageSent) {
        return new ResponseEntity<>(new MessageReceivedDTO(10L, "11.11.2022.", 1L, messageSent.getReceiverId(), messageSent.getMessage(), messageSent.getType(), messageSent.getRideId()), HttpStatus.OK);
    }

    @PostMapping(value = "/user/{id}/note", consumes = "application/json", produces = "application/json")
    public ResponseEntity<NoteDTO> sendNote(@PathVariable Integer id,
                                            @RequestBody NoteMessageDTO messageDTO){
        return new ResponseEntity<>(new NoteDTO(10L, "11.11.2022.", messageDTO.getMessage()), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}/note", produces = "application/json")
    public ResponseEntity<NoteResponseDTO> getNotes(@PathVariable Integer id, Pageable page){
        ArrayList<NoteDTO> notes = new ArrayList<>();
        notes.add(new NoteDTO(10L, "11.11.2022.", "lala"));
        return new ResponseEntity<>(new NoteResponseDTO(notes.size(), notes), HttpStatus.OK);
    }

}
