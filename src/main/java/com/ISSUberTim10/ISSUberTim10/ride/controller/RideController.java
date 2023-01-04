package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserExpandedDTO;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/ride")
@CrossOrigin(origins = "http://localhost:4200")
public class RideController {

    @Autowired
    IRideService rideService;

    @PostMapping(consumes = "application/json", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER')")
    ResponseEntity<RideDTO> addRide(@RequestBody RideCreationDTO rideCreation){
        Ride newRideRequest = new Ride(rideCreation);
        if (!rideService.isBookableRide(newRideRequest)) {
             throw new CustomException("Cannot create a ride while you have one already pending!", HttpStatus.BAD_REQUEST);
        }
        rideService.save(newRideRequest);
        return new ResponseEntity<>(new RideDTO(newRideRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/active", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    ResponseEntity<RideDTO> getRideByDriverId(@PathVariable Integer driverId){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Strazilovska 19, Novi Sad", 45.2501342, 19.8480507), new LocationDTO("Fruskogorska 5, Novi Sad", 45.2523302, 19.7586626)));
        passengers.add(new UserDTO(1L, "sandra@gmail"));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "STANDARD", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
    }

    @GetMapping(value = "/passenger/{passengerId}/active", produces = "application/json")
//    @PreAuthorize(value = "hasRole('PASSENGER') and @userSecurity.hasUserId(authentication, #passengerId, 'Ride')")
    ResponseEntity<RideDTO> getRideByPassengerId(@PathVariable Integer passengerId){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<RideDTO> getRideById(@PathVariable Integer id){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Strazilovska 16, novi sad", 45.2482762, 19.8488143), new LocationDTO("Slovacka 26, novi sad", 45.2572757, 19.8358817)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
//        return rideService.getRideById(id);
    }

    @PutMapping(value = "/{id}/withdraw", produces = "application/json")
    ResponseEntity<RideDTO> cancelRide(@PathVariable Integer id){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/panic", consumes = "application/json", produces = "application/json")
    ResponseEntity<PanicExpandedDTO> addPanic(@PathVariable Integer id, @RequestBody ReasonDTO panic){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        RideDTO ride = new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
        passengers, 5, "", true, true, null, new RejectionDTO("zato", "11.11.2022."));
        UserExpandedDTO user = new UserExpandedDTO(null, "Marija", "Ivkov", "src", "05156", "marija@gmail", "Novi Sad");
        return new ResponseEntity<>(new PanicExpandedDTO(10, user, ride, panic.getReason(), "11.11.2022."), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept", produces = "application/json")
    ResponseEntity<RideDTO> acceptRide(@PathVariable Integer id){
//        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
//        ArrayList<UserDTO> passengers = new ArrayList<>();
//        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
//        passengers.add(new UserDTO(1L, ""));
//        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
//                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
        return rideService.acceptRide(id);
    }

    @PutMapping(value = "/{id}/end", produces = "application/json")
    ResponseEntity<RideDTO> endRide(@PathVariable Integer id){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel", consumes = "application/json", produces = "application/json")
    ResponseEntity<RideDTO> cancelRideWithExplanation(@PathVariable Integer id, @RequestBody ReasonDTO reason){
//        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
//        ArrayList<UserDTO> passengers = new ArrayList<>();
//        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
//        passengers.add(new UserDTO(1L, ""));
//        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
//                passengers, 5, "", true, true, "PENDING", new RejectionDTO(reason.getReason(), "11.11.2022.")), HttpStatus.OK);
        return rideService.cancelRideWithExplanation(id, reason);
    }
}
