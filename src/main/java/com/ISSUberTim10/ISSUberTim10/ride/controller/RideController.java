package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.dto.UserExpandedDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
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
    ResponseEntity<RideDTO> getRideByDriverId(@PathVariable Integer driverId){
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, "sandra@gmail"));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "STANDARD", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
    }

    @GetMapping(value = "/passenger/{passengerId}/active", produces = "application/json")
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
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
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
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO("zato", "11.11.2022.")), HttpStatus.OK);
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
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        ArrayList<UserDTO> passengers = new ArrayList<>();
        locations.add(new DepartureDestinationLocationsDTO(new LocationDTO("Detelinara", 10.0, 10.0), new LocationDTO("Liman1", 10.0, 10.0)));
        passengers.add(new UserDTO(1L, ""));
        return new ResponseEntity<>(new RideDTO(1L, locations, "", "", 123, new UserDTO(1L, ""),
                passengers, 5, "", true, true, "PENDING", new RejectionDTO(reason.getReason(), "11.11.2022.")), HttpStatus.OK);
    }
}
