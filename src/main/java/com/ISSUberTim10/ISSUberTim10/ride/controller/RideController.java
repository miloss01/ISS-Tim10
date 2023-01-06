package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.ride.FavoriteLocation;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserExpandedDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IFavoriteLocationService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IPassengerService;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IVehicleTypeService;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.ride.DepartureDestination;
import com.ISSUberTim10.ISSUberTim10.ride.Location;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/ride")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class RideController {

    @Autowired
    IRideService rideService;
    @Autowired
    IDriverService driverService;
    @Autowired
    IPassengerService passengerService;

    @Autowired
    IFavoriteLocationService favoriteLocationService;

    @Autowired
    IAppUserService appUserService;

    @Autowired
    IVehicleTypeService vehicleTypeService;

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

    @PostMapping(value = "/favorites", consumes = "application/json", produces = "application/json")
    ResponseEntity<FavoriteLocationResponseDTO> saveFavoriteLocation(
            @Valid @RequestBody FavoriteLocationRequestDTO locationRequestDTO) {

        FavoriteLocation location = new FavoriteLocation();

        // Extract real objects from Request DTO
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Passenger maker = (Passenger) appUserService.findByEmail(username).get();

        List<DepartureDestination> locations = new ArrayList<>();
        for (DepartureDestinationLocationsDTO locationDTO : locationRequestDTO.getLocations()) {
            DepartureDestination departureDestination = new DepartureDestination(
                    new Location(locationDTO.getDeparture().getAddress(), locationDTO.getDeparture().getLatitude(), locationDTO.getDeparture().getLongitude()),
                    new Location(locationDTO.getDestination().getAddress(), locationDTO.getDestination().getLatitude(), locationDTO.getDestination().getLongitude())
            );
            locations.add(departureDestination);
        }
        List<Passenger> passengers = new ArrayList<>();
        for (UserResponseDTO userDTO : locationRequestDTO.getPassengers()) {
            passengers.add(passengerService.getPassenger(userDTO.getId()));
        }

        location.setFavoriteName(locationRequestDTO.getFavoriteName());
        location.setLocations(locations);
        location.setPassengers(passengers);
        location.setBabyTransport(locationRequestDTO.isBabyTransport());
        location.setPetTransport(locationRequestDTO.isPetTransport());
        location.setVehicleType(vehicleTypeService.getByName(Vehicle.VEHICLE_TYPE.valueOf(locationRequestDTO.getVehicleType())));
        location.setMakerId(maker.getId());
        FavoriteLocation saved = favoriteLocationService.save(location, maker.getId());


        // Transform saved into Response DTO
        FavoriteLocationResponseDTO responseDTO = new FavoriteLocationResponseDTO();
        List<DepartureDestinationLocationsDTO> locationsDTOS = new ArrayList<>();
        for (DepartureDestination dd : saved.getLocations()) {
            locationsDTOS.add(new DepartureDestinationLocationsDTO(dd));
        }
        List<UserResponseDTO> passengersDTOS = new ArrayList<>();
        for (Passenger p : saved.getPassengers()) {
            passengersDTOS.add(new UserResponseDTO(p));
        }
        responseDTO.setId(saved.getId());
        responseDTO.setFavoriteName(saved.getFavoriteName());
        responseDTO.setLocations(locationsDTOS);
        responseDTO.setPassengers(passengersDTOS);
        responseDTO.setBabyTransport(saved.isBabyTransport());
        responseDTO.setPetTransport(saved.isPetTransport());
        responseDTO.setVehicleType(saved.getVehicleType().getName().toString());

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/favorites", produces = "application/json")
    ResponseEntity<List<FavoriteLocationResponseDTO>> getFavoriteLocations() {

        // Extract passenger from JWT to get their locations
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Passenger maker = (Passenger) appUserService.findByEmail(username).get();

        // Transform real objects into DTOs
        List<FavoriteLocation> locations = favoriteLocationService.getByMaker(maker.getId());
        List<FavoriteLocationResponseDTO> locationResponseDTOS = new ArrayList<>();
        for (FavoriteLocation l : locations) {
            FavoriteLocationResponseDTO responseDTO = new FavoriteLocationResponseDTO();
            List<DepartureDestinationLocationsDTO> locationsDTOS = new ArrayList<>();
            for (DepartureDestination dd : l.getLocations()) {
                locationsDTOS.add(new DepartureDestinationLocationsDTO(dd));
            }
            List<UserResponseDTO> passengersDTOS = new ArrayList<>();
            for (Passenger p : l.getPassengers()) {
                passengersDTOS.add(new UserResponseDTO(p));
            }
            responseDTO.setId(l.getId());
            responseDTO.setFavoriteName(l.getFavoriteName());
            responseDTO.setLocations(locationsDTOS);
            responseDTO.setPassengers(passengersDTOS);
            responseDTO.setBabyTransport(l.isBabyTransport());
            responseDTO.setPetTransport(l.isPetTransport());
            responseDTO.setVehicleType(l.getVehicleType().getName().toString());
            locationResponseDTOS.add(responseDTO);
        }

        return new ResponseEntity<>(locationResponseDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value = "/favorites/{id}")
    public ResponseEntity<String> deleteFavoriteLocation(@PathVariable Integer id) {

        // Throws 404 if not found
        favoriteLocationService.getById(id.longValue());

        favoriteLocationService.delete(id);
        return new ResponseEntity<>("Successful deletion of favorite location!", HttpStatus.NO_CONTENT);
    }

}
