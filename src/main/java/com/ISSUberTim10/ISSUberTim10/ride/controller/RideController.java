package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.helper.StringFormatting;
import com.ISSUberTim10.ISSUberTim10.ride.*;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IFavoriteLocationService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IPassengerService;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IVehicleTypeService;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IDriverService;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IPanicService;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@EnableScheduling
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

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    @Autowired
    NotificationSchedule notificationSchedule;


    @PostMapping(consumes = "application/json", produces = "application/json")
    @PreAuthorize(value = "hasRole('DRIVER') or hasRole('PASSENGER')")
    ResponseEntity<RideDTO> addRide(@Valid @RequestBody RideCreationDTO rideCreation){
        System.out.println("Usao u zakazivanje");
        System.out.println(rideCreation.getDistance());
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
                        saved.getId().intValue(), ""));
        this.simpMessagingTemplate.convertAndSend("/ride-notification-driver-request-mob/" + saved.getDriver().getId(), rideDTO);
        for (Passenger p : saved.getPassengers()) {
            this.simpMessagingTemplate.convertAndSend("/ride-notification-passenger/" + p.getId(),
                    new NotificationDTO("Driver has been appointed.\nHang on and wait for their acceptance.", saved.getId().intValue(), ""));
        }
        return new ResponseEntity<>(rideDTO, HttpStatus.OK);

    }

    @GetMapping(value = "/driver/{driverId}/active", produces = "application/json")
   // @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    @PreAuthorize(value = "hasRole('DRIVER')")
    ResponseEntity<RideDTO> getRideByDriverId(@PathVariable Integer driverId){

        Driver driver = driverService.getById(driverId.longValue());


        ArrayList<Ride.RIDE_STATUS> statuses = new ArrayList<>();
        statuses.add(Ride.RIDE_STATUS.pending);
        statuses.add(Ride.RIDE_STATUS.active);

        Ride ride = rideService.getActiveDriverRide(driver);

        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/accepted", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    @PreAuthorize(value = "hasRole('DRIVER')")
    ResponseEntity<RideDTO> getAcceptedRideByDriverId(@PathVariable Integer driverId){

        Driver driver = driverService.getById(driverId.longValue());

        Ride ride = rideService.getDriverEarliestAcceptedRide(driver);

        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/pending", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    @PreAuthorize(value = "hasRole('DRIVER')")
    ResponseEntity<RideDTO> getPendingRideByDriverId(@PathVariable Integer driverId){

        Driver driver = driverService.getById(driverId.longValue());

        Ride ride = rideService.getByDriverAndStatus(driver, Ride.RIDE_STATUS.pending);


//        ArrayList<Ride> rides = rideService.getByDriverAndStatus(driver, statuses);

//        if (rides.size() > 1)
//            throw new CustomException("Multiple rides in active and/or pending status", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @GetMapping(value = "/passenger/{passengerId}/active", produces = "application/json")
    @PreAuthorize(value = "hasRole('PASSENGER')")
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
    @PreAuthorize(value = "hasRole('PASSENGER')")
    ResponseEntity<RideDTO> cancelRide(@PathVariable Integer id){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.withdrawRide(ride);
        RideDTO rideDTO = new RideDTO(ride);
        this.simpMessagingTemplate.convertAndSend("/ride-notification-driver-withdrawal/" + ride.getDriver().getId(),
                new NotificationDTO("Ride was supposed to start at " + rideDTO.getStartTime() +
                        " at location " + rideDTO.getLocations().get(0).getDeparture().getAddress() + ".", ride.getId().intValue(), ""));
        notificationSchedule.removeToBeReminded(ride);
        return new ResponseEntity<>(rideDTO, HttpStatus.OK);
}

    @PutMapping(value = "/{id}/panic", consumes = "application/json", produces = "application/json")
    @PreAuthorize(value = "hasRole('PASSENGER') or hasRole('DRIVER')")
    ResponseEntity<PanicExpandedDTO> addPanic(@PathVariable Integer id,@Valid @RequestBody ReasonDTO panicReason){
        Ride ride = rideService.getRideById(id.longValue());
        // Extract user who activated panic from JWT
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        AppUser user = appUserService.findByEmail(userDetails.getUsername());
        Panic panic = new Panic(0L, user, ride, LocalDateTime.now(), panicReason.getReason());
        panic = panicService.save(panic);

        String ret = panic.getAppUser().getName() + " " + panic.getAppUser().getLastName() + " panicked during ride with " + ride.getDriver().getName() + " " + ride.getDriver().getLastName() + " as driver and stated the folowing reason: " + panic.getReason();
        simpMessagingTemplate.convertAndSend("/ride-notification-panic", new NotificationDTO(ret, panic.getRide().getId().intValue(), "PANIC"));

        return new ResponseEntity<>(new PanicExpandedDTO(panic), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/start", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    @PreAuthorize(value = "hasRole('DRIVER')")
    ResponseEntity<RideDTO> startRide(@PathVariable Integer id){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.startRide(ride);
        for (Passenger p : ride.getPassengers()) {
            this.simpMessagingTemplate.convertAndSend("/ride-notification-passenger/" + p.getId(),
                    new NotificationDTO("Ride has started!", ride.getId().intValue(), "START_RIDE"));
        }
        notificationSchedule.removeToBeReminded(ride);
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept", produces = "application/json")
    //@PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    @PreAuthorize(value = "hasRole('DRIVER')")
    ResponseEntity<RideDTO> acceptRide(@PathVariable Integer id){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.acceptRide(ride);
        for (Passenger p : ride.getPassengers()) {
            this.simpMessagingTemplate.convertAndSend("/ride-notification-passenger/" + p.getId(),
                    new NotificationDTO("Driver has accepted your ride request! You'll be riding with " +
                            ride.getDriver().getName() + " " + ride.getDriver().getLastName(), ride.getId().intValue(), "ACCEPT_RIDE"));
            this.simpMessagingTemplate.convertAndSend("/vehicle-time/" + p.getId(), ride.getEstimatedTimeMinutes());
        }
        notificationSchedule.addToBeReminded(ride);
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    @PreAuthorize(value = "hasRole('DRIVER')")
    ResponseEntity<RideDTO> endRide(@PathVariable Integer id){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.endRide(ride);
        for (Passenger p : ride.getPassengers()) {
            this.simpMessagingTemplate.convertAndSend("/ride-notification-passenger/" + p.getId(),
                    new NotificationDTO("Ride has ended.", ride.getId().intValue(), "END_RIDE"));
        }
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel", consumes = "application/json", produces = "application/json")
//    @PreAuthorize(value = "hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #driverId, 'Ride')")
    @PreAuthorize(value = "hasRole('DRIVER')")
    ResponseEntity<RideDTO> cancelRideWithExplanation(@PathVariable Integer id,@Valid @RequestBody ReasonDTO reason){
        Ride ride = rideService.getRideById(id.longValue());
        ride = rideService.cancelRideWithExplanation(ride, reason.getReason());
        for (Passenger p : ride.getPassengers()) {
            this.simpMessagingTemplate.convertAndSend("/ride-notification-passenger/" + p.getId(),
                    new NotificationDTO("Driver has backed out and cancelled the ride. He provided the following explanation: \"" + reason.getReason() + "\"", ride.getId().intValue(), "DRIVER_CANCEL"));
        }
        notificationSchedule.removeToBeReminded(ride);
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @PostMapping(value = "/favorites", consumes = "application/json", produces = "application/json")
    @PreAuthorize(value = "hasRole('PASSENGER')")
    ResponseEntity<FavoriteLocationResponseDTO> saveFavoriteLocation(
            @Valid @RequestBody FavoriteLocationRequestDTO locationRequestDTO) {

        FavoriteLocation location = new FavoriteLocation();

        // Extract real objects from Request DTO
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Passenger maker = (Passenger) appUserService.findByEmail(username);

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
    @PreAuthorize(value = "hasRole('PASSENGER')")
    ResponseEntity<List<FavoriteLocationResponseDTO>> getFavoriteLocations() {

        // Extract passenger from JWT to get their locations
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Passenger maker = (Passenger) appUserService.findByEmail(username);

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
    @PreAuthorize(value = "hasRole('PASSENGER')")
    public ResponseEntity<String> deleteFavoriteLocation(@PathVariable Integer id) {

        // Throws 404 if not found
        favoriteLocationService.getById(id.longValue());

        favoriteLocationService.delete(id);
        return new ResponseEntity<>("Successful deletion of favorite location!", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/getAllRides", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> getAllRides(Pageable page,
                                                       @RequestParam(required = false) String from,
                                                       @RequestParam(required = false) String to) {

        List<Ride> rides = rideService.getAll(page);

        ArrayList<RideDTO> rideDTOs = new ArrayList<>();

        LocalDateTime fromDate;
        LocalDateTime toDate;

        if (from == null)
            fromDate = LocalDateTime.of(2000, 1, 1, 1, 1);
        else
            fromDate = LocalDateTime.parse(from, StringFormatting.dateTimeFormatterWithSeconds);

        if (to == null)
            toDate = LocalDateTime.of(3000, 1, 1, 1, 1);
        else
            toDate = LocalDateTime.parse(to, StringFormatting.dateTimeFormatterWithSeconds);

        for (Ride ride : rides)
            if (ride.getStartTime() != null &&
                    ride.getEndTime() != null &&
                    ride.getStartTime().isAfter(fromDate) &&
                    ride.getEndTime().isBefore(toDate))
                rideDTOs.add(new RideDTO(ride));

        return new ResponseEntity<>(
                new RideResponseDTO(rideDTOs.size(), rideDTOs),
                HttpStatus.OK
        );
    }

    }
