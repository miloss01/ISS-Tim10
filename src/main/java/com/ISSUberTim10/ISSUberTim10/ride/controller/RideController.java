package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.ride.FavoriteLocation;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserExpandedDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IFavoriteLocationService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IPassengerService;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IVehicleTypeService;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.ride.DepartureDestination;
import com.ISSUberTim10.ISSUberTim10.ride.Location;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ride")
@CrossOrigin(origins = "http://localhost:4200")
public class RideController {

    @Autowired
    IRideService rideService;

    @Autowired
    IFavoriteLocationService favoriteLocationService;

    @Autowired
    IPassengerService passengerService;

    @Autowired
    IAppUserService appUserService;

    @Autowired
    IVehicleTypeService vehicleTypeService;


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

    @PostMapping(value = "/favorites", consumes = "application/json", produces = "application/json")
    ResponseEntity<FavoriteLocationResponseDTO> saveFavoriteLocation(
            @RequestBody FavoriteLocationRequestDTO locationRequestDTO) {

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
            System.out.println(l.getFavoriteName());
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
