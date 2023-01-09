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
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping(consumes = "application/json", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER')")
    ResponseEntity<RideDTO> addRide(@RequestBody RideCreationDTO rideCreation){
        Ride newRideRequest = new Ride(rideCreation);

        // throws 404 if passenger already in active ride
        // returns no content if no available vehicles/drivers
        if (!rideService.isBookableRide(newRideRequest)) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        Ride saved = rideService.save(newRideRequest);
        System.out.println(newRideRequest.getRideStatus().toString());
        RideDTO rideDTO = new RideDTO(saved);
        this.simpMessagingTemplate.convertAndSend("/ride-notification-driver-request/" + saved.getDriver().getId(),
                new NotificationDTO("From " +
                        rideDTO.getLocations().get(0).getDeparture().getAddress() +
                        " to " + rideDTO.getLocations().get(0).getDestination().getAddress(),
                        saved.getId().intValue()));
        for (Passenger p : saved.getPassengers()) {
            this.simpMessagingTemplate.convertAndSend("/ride-notification-passenger/" + p.getId(),
                    new NotificationDTO("Driver has been appointed.\nHang on and wait for their acceptance.", saved.getId().intValue()));
        }
        return new ResponseEntity<>(rideDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/active", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    ResponseEntity<RideDTO> getRideByDriverId(@PathVariable Integer driverId){

        Driver driver = driverService.getById(driverId.longValue());

        Ride ride = rideService.getActiveDriverRide(driver);

        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/accepted", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    ResponseEntity<RideDTO> getAcceptedRideByDriverId(@PathVariable Integer driverId){

        Driver driver = driverService.getById(driverId.longValue());

        Ride ride = rideService.getDriverEarliestAcceptedRide(driver);

        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/pending", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    ResponseEntity<RideDTO> getPendingRideByDriverId(@PathVariable Integer driverId){

        Driver driver = driverService.getById(driverId.longValue());

        Ride ride = rideService.getByDriverAndStatus(driver, Ride.RIDE_STATUS.pending);

        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @GetMapping(value = "/passenger/{passengerId}/active", produces = "application/json")
//    @PreAuthorize(value = "hasRole('PASSENGER') and @userSecurity.hasUserId(authentication, #passengerId, 'Ride')")
    ResponseEntity<RideDTO> getRideByPassengerId(@PathVariable Integer passengerId){

        Passenger passenger = passengerService.getPassenger(passengerId.longValue());

        Ride ride = rideService.getByPassengerAndStatus(passenger, Ride.RIDE_STATUS.active);

        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @GetMapping(value = "/passenger/{passengerId}/accepted", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    ResponseEntity<RideDTO> getAcceptedRideByPassengerId(@PathVariable Integer passengerId){

        Passenger passenger = passengerService.getPassenger(passengerId.longValue());

        Ride ride = rideService.getByPassengerAndStatus(passenger, Ride.RIDE_STATUS.accepted);

        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @GetMapping(value = "/passenger/{passengerId}/pending", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    ResponseEntity<RideDTO> getPendingRideByPassengerId(@PathVariable Integer passengerId) {

        Passenger passenger = passengerService.getPassenger(passengerId.longValue());

        Ride ride = rideService.getByPassengerAndStatus(passenger, Ride.RIDE_STATUS.pending);

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
        this.simpMessagingTemplate.convertAndSend("/ride-notification-driver-withdrawal/" + ride.getDriver().getId(),
                new NotificationDTO("Passenger has backed out and cancelled the ride.", ride.getId().intValue()));
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
        for (Passenger p : ride.getPassengers()) {
            this.simpMessagingTemplate.convertAndSend("/ride-notification-passenger/" + p.getId(),
                    new NotificationDTO("Ride has started!", ride.getId().intValue()));
        }
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept", produces = "application/json")
    ResponseEntity<RideDTO> acceptRide(@PathVariable Integer id){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.acceptRide(ride);
        for (Passenger p : ride.getPassengers()) {
            this.simpMessagingTemplate.convertAndSend("/ride-notification-passenger/" + p.getId(),
                    new NotificationDTO("Driver has accepted your ride request! You'll be riding with " +
                            ride.getDriver().getName() + " " + ride.getDriver().getLastName(), ride.getId().intValue()));
        }
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end", produces = "application/json")
    ResponseEntity<RideDTO> endRide(@PathVariable Integer id){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.endRide(ride);
        for (Passenger p : ride.getPassengers()) {
            this.simpMessagingTemplate.convertAndSend("/ride-notification-passenger/" + p.getId(),
                    new NotificationDTO("Ride has ended.", ride.getId().intValue()));
        }
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel", consumes = "application/json", produces = "application/json")
    ResponseEntity<RideDTO> cancelRideWithExplanation(@PathVariable Integer id, @RequestBody ReasonDTO reason){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.cancelRideWithExplanation(ride, reason.getReason());
        for (Passenger p : ride.getPassengers()) {
            this.simpMessagingTemplate.convertAndSend("/ride-notification-passenger/" + p.getId(),
                    new NotificationDTO("Driver has backed out and cancelled the ride.", ride.getId().intValue()));
        }
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }
}
