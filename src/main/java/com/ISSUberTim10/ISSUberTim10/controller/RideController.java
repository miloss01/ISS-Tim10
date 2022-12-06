package com.ISSUberTim10.ISSUberTim10.controller;

import com.ISSUberTim10.ISSUberTim10.dto.*;
import com.ISSUberTim10.ISSUberTim10.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/ride")
public class RideController {

    @Autowired
    IRideService rideService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<RideDTO> addRide(@RequestBody RideCreationDTO rideCreation){
        return new ResponseEntity<>(new RideDTO(1L, rideCreation.getLocations(), "", "", 123, new UserDTO(1L, ""),
                rideCreation.getPassengers(), 5, rideCreation.getVehicleType(), rideCreation.isBabyTransport(), rideCreation.isPetTransport(), "PENDING", new RejectionDTO("zato", "11.11.2022.")),
                HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/active", produces = "application/json")
    ResponseEntity<RideDTO> getRideByDriverId(@PathVariable Long id){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
    }

    @GetMapping(value = "/passenger/{passengerId}/active", produces = "application/json")
    ResponseEntity<RideDTO> getRideByPassengerId(@PathVariable Long id){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<RideDTO> getRideById(@PathVariable Long id){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/withdraw")
    ResponseEntity<RideDTO> cancelRide(@PathVariable Long id){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/panic", consumes = "application/json", produces = "application/json")
    ResponseEntity<PanicResponseDTO> addPanic(@PathVariable Long id, @RequestBody ReasonDTO panic){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        RideDTO ride = new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
        passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022."));
        UserExpandedDTO user = new UserExpandedDTO(1L, "Marija", "Ivkov", "src", "05156", "marija@gmail", "Novi Sad");
        return new ResponseEntity<>(new PanicResponseDTO(10L, user, ride, panic.getReason(), "11.11.2022."), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept", produces = "application/json")
    ResponseEntity<RideDTO> acceptRide(@PathVariable Long id){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end", produces = "application/json")
    ResponseEntity<RideDTO> endRide(@PathVariable Long id){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel", consumes = "application/json", produces = "application/json")
    ResponseEntity<RideDTO> cancelRideWithExplanation(@PathVariable Long id, @RequestBody ReasonDTO reason){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO(reason.getReason(), "11.11.2022.")), HttpStatus.OK);
    }
}
