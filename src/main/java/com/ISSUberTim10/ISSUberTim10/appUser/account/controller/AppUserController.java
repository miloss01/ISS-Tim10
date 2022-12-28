package com.ISSUberTim10.ISSUberTim10.appUser.account.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.Role;
import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.*;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.auth.JwtTokenUtil;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class AppUserController {

    @Autowired
    IAppUserService service;

    @Autowired
    IRideService rideService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @GetMapping(value = "/user2", produces = "application/json")
    public ResponseEntity<Collection<AppUser>> getAll() {
        Collection<AppUser> users = service.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<AllUsersDTO> getAll(Pageable page) {
        Page<AppUser> users = service.getAll(page);
        ArrayList<UserExpandedDTO> usersDTO = new ArrayList<>();
        usersDTO.add(new UserExpandedDTO(1L, "Marija", "Ivkov", "src", "05156", "marija@gmail", "Novi Sad"));
        usersDTO.add(new UserExpandedDTO(1L, "Marija", "Ivkov", "src", "05156", "marija@gmail", "Novi Sad"));
        return new ResponseEntity<>(new AllUsersDTO(usersDTO.size(), usersDTO), HttpStatus.OK);
    }

    @GetMapping(value="/1", produces = "application/json")
    public ResponseEntity<UserExpandedDTO> getById(@RequestParam Integer id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }



    @PostMapping()
    public void createAll() {
        service.createAll();
    }

    @DeleteMapping()
    public void deleteAll() {
        service.deleteAll();
    }

    @GetMapping(value = "/{id}/ride", produces = "application/json")
    public ResponseEntity<RideResponseDTO> getUsersRides(@PathVariable Integer id,
                                                         Pageable page,
                                                         @RequestParam(required = false) String sort,
                                                         @RequestParam(required = false) String from,
                                                         @RequestParam(required = false) String to) {
        Page<Ride> resultPage = rideService.getByUser(id.longValue(), page);
        List<Ride> rides;
        ArrayList<RideDTO> ridesDTO = new ArrayList<>();
        rides = resultPage.getContent();
        for (Ride ride : rides) {
            ridesDTO.add(new RideDTO(ride));
        }
        RideResponseDTO responseDTO = new RideResponseDTO(ridesDTO.size(), ridesDTO);

//
//        ArrayList<RideDTO> ridesDTO = new ArrayList<>();
//        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
//        ArrayList<UserDTO> passengers = new ArrayList<>();
//        passengers.add(new UserDTO(2L, "pepe"));
//        passengers.add(new UserDTO(2L, "guug"));
//        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
//        passengers.add(new UserDTO(1L, "eheh"));
//        ridesDTO.add(new RideDTO(1L, locations, "12.10.2022. 11:17", "10.10.2022. 11:00", 123, new UserDTO(1L, "didi"),
//                passengers, 5, "", true, true, null, new RejectionDTO("zato", "11.11.2022.")));
//        ridesDTO.add(new RideDTO(1L, locations, "05.12.202. 11:00", "10.10.2022. 11:00", 123, new UserDTO(1L, "didi"),
//                passengers, 5, "", true, true, null, new RejectionDTO("zato", "11.11.2022.")));
//        ridesDTO.add(new RideDTO(1L, locations, "05.12.202. 11:00", "10.10.2022. 11:00", 123, new UserDTO(1L, "didi"),
//                passengers, 5, "", true, true, null, new RejectionDTO("zato", "11.11.2022.")));
        return new ResponseEntity<>(new RideResponseDTO(ridesDTO.size(), ridesDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginDTO loginDTO){

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        String role = sc.getAuthentication().getAuthorities().toString();
        AppUser user = service.findByEmail(loginDTO.getEmail()).get();

        String token = jwtTokenUtil.generateToken(
            loginDTO.getEmail(),
            Role.valueOf(role.substring(role.indexOf("_") + 1, role.length() - 1)),
            user.getId());

        return new ResponseEntity<>(
                new TokenResponseDTO(token, ""),
                HttpStatus.OK
        );

    }

    @PutMapping(value = "/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable Integer id) {
        try {
            return service.blockUser(id);
        } catch (Exception responseStatusException) {
            return new ResponseEntity<>(responseStatusException.getMessage(), HttpStatus.NOT_FOUND);
        }
        //return new ResponseEntity<>("User successfully blocked", HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/unblock")
    public ResponseEntity<String> unblockUser(@PathVariable Integer id) {
        try {
            return service.unblockUser(id);
        } catch (Exception responseStatusException) {
            return new ResponseEntity<>(responseStatusException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/message", produces = "application/json")
    public ResponseEntity<MessageResponseDTO> getMessagesById(@PathVariable Integer id) {
        ArrayList<MessageReceivedDTO> messages = new ArrayList<>();
        messages.add(new MessageReceivedDTO(10L, "11.11.2022.", 1L, 2L, "Message", "RIDE", 3L));
        return new ResponseEntity<>(new MessageResponseDTO(messages.size(), messages), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/message", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MessageReceivedDTO> sendMessagesById(@PathVariable Integer id,
                                                              @RequestBody MessageSentDTO messageSent) {
        return new ResponseEntity<>(new MessageReceivedDTO(10L, "11.11.2022.", 1L, messageSent.getReceiverId(), messageSent.getMessage(), messageSent.getType(), messageSent.getRideId()), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/note", consumes = "application/json", produces = "application/json")
    public ResponseEntity<NoteDTO> sendNote(@PathVariable Integer id,
                                            @RequestBody NoteMessageDTO messageDTO) {

        return service.sendMessage(id, messageDTO);
    }

    @GetMapping(value = "/{id}/note", produces = "application/json")
    public ResponseEntity<NoteResponseDTO> getNotes(@PathVariable Integer id,
                                                    @RequestParam(required = false) Pageable page){
        return service.getNotes(id, null);
    }

    @GetMapping(value = "/isBlocked", produces = "application/json")
    public ResponseEntity<IsBlockedDTO> isBlocked(@RequestParam Integer id){
        return service.isBlocked(id);
    }

}
