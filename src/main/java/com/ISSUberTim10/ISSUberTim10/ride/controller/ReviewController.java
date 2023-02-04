package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl.AppUserService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl.PassengerService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IPassengerService;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.*;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IDriverService;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IVehicleService;
import com.ISSUberTim10.ISSUberTim10.auth.JwtTokenUtil;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.ride.Review;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideReviewDTO;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IReviewService;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @Autowired
    private IRideService rideService;

    @Autowired
    private IVehicleService vehicleService;

    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private IDriverService driverService;

    // Creating a review about the vehicle
    @PostMapping(value = "/{rideId}/vehicle", consumes = "application/json", produces = "application/json")
//    @PreAuthorize(value = "hasRole('PASSENGER') and @userSecurity.hasUserId(authentication, #id, 'Review')")
    public ResponseEntity<VehicleReviewResponseDTO> saveVehicleReview(
            @PathVariable Integer rideId,
            @Valid @RequestBody VehicleReviewRequestDTO vehicleReviewRequestDTO)
    {
        // Throws 404 if not found
        Ride ride = rideService.getRideById(rideId.longValue());
        Vehicle vehicle = vehicleService.getById(ride.getDriver().getVehicle().getId());

        // Extract passenger who left the review from JWT
       UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Passenger passenger = (Passenger) appUserService.findByEmail(username);

        Review review = new Review();
        review.setRating(vehicleReviewRequestDTO.getRating());
        review.setComment(vehicleReviewRequestDTO.getComment());
        review.setRide(ride);
        review.setPassenger(passenger);
        review.setForDriver(false);
        Review saved = reviewService.saveVehicleReview(review);

        return new ResponseEntity<>(
                new VehicleReviewResponseDTO(
                    saved.getId().intValue(),
                    saved.getRating(), saved.getComment(),
                    new UserResponseDTO(saved.getPassenger().getId(), saved.getPassenger().getEmail())),
                HttpStatus.OK);
    }


    // Get the reviews for the specific vehicle
    @GetMapping(value = "/vehicle/{id}", produces = "application/json")
//    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('PASSENGER') and @userSecurity.hasUserId(authentication, #id, 'Review'))")
    public ResponseEntity<VehicleReviewsDTO> getVehicleReviews(@PathVariable Integer id) {

        Vehicle vehicle = vehicleService.getById(id.longValue());

        List<Review> reviews = reviewService.getVehicleReviews(vehicle.getId().intValue());
        List<VehicleReviewResponseDTO> reviewsDTOs = new ArrayList<>();
        for (Review review : reviews) {
            reviewsDTOs.add(new VehicleReviewResponseDTO(
                    review.getId().intValue(),
                    review.getRating(),
                    review.getComment(),
                    new UserResponseDTO(review.getPassenger().getId(), review.getPassenger().getEmail())
            ));
        }
        VehicleReviewsDTO reviewsDTO = new VehicleReviewsDTO(reviewsDTOs.size(), reviewsDTOs);

        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

    @PostMapping(value="/{rideId}/driver", consumes = "application/json", produces = "application/json")
//    @PreAuthorize(value = "hasRole('PASSENGER')")
    public ResponseEntity<DriverReviewResponseDTO> saveDriverReview(
            @PathVariable Integer rideId,
            @Valid @RequestBody DriverReviewRequestDTO driverReviewRequestDTO) {

        // Throws 404 if not found
        Ride ride = rideService.getRideById(rideId.longValue());

        // Extract passenger who left the review from JWT
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Passenger passenger = (Passenger) appUserService.findByEmail(username);

        Review review = new Review();
        review.setRating(driverReviewRequestDTO.getRating());
        review.setComment(driverReviewRequestDTO.getComment());
        review.setRide(ride);
        review.setPassenger(passenger);
        review.setForDriver(true);
        Review saved = reviewService.saveDriverReview(review);

        return new ResponseEntity<>(
                new DriverReviewResponseDTO(
                        saved.getId().intValue(),
                        saved.getRating(), saved.getComment(),
                        new UserResponseDTO(saved.getPassenger().getId(), saved.getPassenger().getEmail())),
                HttpStatus.OK);
    }

    // Get the reviews for the specific driver
    @GetMapping(value = "/driver/{id}", produces = "application/json")
//    @PreAuthorize(value = "hasRole('ADMIN') or (hasRole('DRIVER') and @userSecurity.hasUserId(authentication, #id, 'Review'))")
    public ResponseEntity<DriverReviewsDTO> getDriverReviews(@PathVariable Integer id) {

        Driver driver = driverService.getById(id.longValue());

        List<Review> reviews = reviewService.getDriverReviews(driver.getId().intValue());
        List<DriverReviewResponseDTO> reviewsDTOs = new ArrayList<>();
        for (Review review : reviews) {
            reviewsDTOs.add(new DriverReviewResponseDTO(
                    review.getId().intValue(),
                    review.getRating(),
                    review.getComment(),
                    new UserResponseDTO(review.getPassenger().getId(), review.getPassenger().getEmail())
            ));
        }
        DriverReviewsDTO reviewsDTO = new DriverReviewsDTO(reviewsDTOs.size(), reviewsDTOs);

        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

    // Overview of both reviews for the specific ride (driver and vehicle)
    @GetMapping(value = "/{rideId}", produces = "application/json")
    public ResponseEntity<List<RideReviewDTO>> getRideReviews(@PathVariable Integer rideId) {

        Ride ride = rideService.getRideById(rideId.longValue());

        List<RideReviewDTO> results = new ArrayList<>();

        // Username : Review[]
        HashMap<String, List<Review>> reviewsMap = reviewService.getRideReviews(ride.getId().intValue());
        for (Map.Entry<String, List<Review>> pair : reviewsMap.entrySet()) {
            RideReviewDTO rideReviewDTO = new RideReviewDTO();
            for (Review review : pair.getValue()) {
                if (review.isForDriver()) {
                    rideReviewDTO.setDriverReview(new DriverReviewResponseDTO(
                            review.getId().intValue(),
                            review.getRating(),
                            review.getComment(),
                            new UserResponseDTO(review.getPassenger().getId(), review.getPassenger().getEmail())
                    ));
                }
                else {
                    rideReviewDTO.setVehicleReview(new VehicleReviewResponseDTO(
                            review.getId().intValue(),
                            review.getRating(),
                            review.getComment(),
                            new UserResponseDTO(review.getPassenger().getId(), review.getPassenger().getEmail())
                    ));
                }
            }
            results.add(rideReviewDTO);
        }

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

}
