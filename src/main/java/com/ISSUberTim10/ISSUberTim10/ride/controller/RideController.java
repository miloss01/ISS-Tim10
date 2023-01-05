package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IPassengerService;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IDriverService;
import com.ISSUberTim10.ISSUberTim10.ride.Panic;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IPanicService;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/ride")
@CrossOrigin(origins = "http://localhost:4200")
public class RideController {

    @Autowired
    IRideService rideService;
    @Autowired
    IDriverService driverService;
    @Autowired
    IPassengerService passengerService;

    @Autowired
    IAppUserService appUserService;

    @Autowired
    IPanicService panicService;

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

        Driver driver = driverService.getById(driverId.longValue());

        Ride ride = rideService.getByDriverAndStatus(driver, Ride.RIDE_STATUS.active);

        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @GetMapping(value = "/passenger/{passengerId}/active", produces = "application/json")
//    @PreAuthorize(value = "hasRole('PASSENGER') and @userSecurity.hasUserId(authentication, #passengerId, 'Ride')")
    ResponseEntity<RideDTO> getRideByPassengerId(@PathVariable Integer passengerId){

        Passenger passenger = passengerService.getPassenger(passengerId.longValue());

        Ride ride = rideService.getByPassengerAndStatus(passenger, Ride.RIDE_STATUS.active);

        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<RideDTO> getRideById(@PathVariable Integer id){
        Ride ride = rideService.getRideById(id.longValue());
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/withdraw", produces = "application/json")
    ResponseEntity<RideDTO> cancelRide(@PathVariable Integer id){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.withdrawRide(ride);
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
}

    @PutMapping(value = "/{id}/panic", consumes = "application/json", produces = "application/json")
    ResponseEntity<PanicExpandedDTO> addPanic(@PathVariable Integer id, @RequestBody ReasonDTO panicReason){
        Ride ride = rideService.getRideById(id.longValue());
        AppUser user = new Passenger(); //TODO razmisli malo ne moras sve milosa da pitas
        Panic panic = new Panic(0L, user, ride, LocalDateTime.now(), panicReason.getReason());
        panic = panicService.save(panic);

        return new ResponseEntity<>(new PanicExpandedDTO(panic), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/start", produces = "application/json")
    ResponseEntity<RideDTO> startRide(@PathVariable Integer id){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.startRide(ride);
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept", produces = "application/json")
    ResponseEntity<RideDTO> acceptRide(@PathVariable Integer id){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.acceptRide(ride);
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end", produces = "application/json")
    ResponseEntity<RideDTO> endRide(@PathVariable Integer id){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.endRide(ride);
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel", consumes = "application/json", produces = "application/json")
    ResponseEntity<RideDTO> cancelRideWithExplanation(@PathVariable Integer id, @RequestBody ReasonDTO reason){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.cancelRideWithExplanation(ride, reason.getReason());
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }
}
