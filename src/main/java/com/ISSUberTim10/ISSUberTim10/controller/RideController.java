package com.ISSUberTim10.ISSUberTim10.controller;

import com.ISSUberTim10.ISSUberTim10.dto.ReasonDTO;
import com.ISSUberTim10.ISSUberTim10.dto.PanicResponseDTO;
import com.ISSUberTim10.ISSUberTim10.dto.RideCreationDTO;
import com.ISSUberTim10.ISSUberTim10.dto.RideDTO;
import com.ISSUberTim10.ISSUberTim10.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ride")
public class RideController {

    @Autowired
    IRideService rideService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<RideDTO> addRide(@RequestBody RideCreationDTO rideCreation){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/active/{driverId}", produces = "application/json")
    ResponseEntity<RideDTO> getRideByDriverId(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/active/{passengerId}", produces = "application/json")
    ResponseEntity<RideDTO> getRideByPassengerId(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<RideDTO> getRideById(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<String> cancelRide(@PathVariable Long id){
        return new ResponseEntity<>("Ride successfully canceled", HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/panic", consumes = "application/json", produces = "application/json")
    ResponseEntity<PanicResponseDTO> addPanic(@PathVariable Long id, @RequestBody ReasonDTO panic){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept", produces = "application/json")
    ResponseEntity<RideDTO> acceptRide(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end", produces = "application/json")
    ResponseEntity<RideDTO> endRide(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel", consumes = "application/json", produces = "application/json")
    ResponseEntity<RideDTO> cancelRideWithExplanation(@PathVariable Long id, @RequestBody ReasonDTO reason){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
