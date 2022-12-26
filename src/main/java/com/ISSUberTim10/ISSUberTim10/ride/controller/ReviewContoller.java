package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.*;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserResponseDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideReviewDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewContoller {

    // Creating a review about the vehicle
    @PostMapping(value = "/{rideId}/vehicle/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<VehicleReviewResponseDTO> saveVehicleReview(@PathVariable Integer rideId,
                                                                      @PathVariable Integer id,
                                                                      @RequestBody VehicleReviewRequestDTO vehicleReviewRequestDTO) {
        return new ResponseEntity<>(new VehicleReviewResponseDTO(4, 3, "amazing", new UserResponseDTO(2, "em@ail.com")), HttpStatus.OK);
    }


    // Get the reviews for the specific vehicle
    @GetMapping(value = "/vehicle/{id}", produces = "application/json")
    public ResponseEntity<VehicleReviewsDTO> getVehicleReviews(@PathVariable Integer id) {

        VehicleReviewResponseDTO reviewDTO = new VehicleReviewResponseDTO(1, 5, "amazing", new UserResponseDTO(2, "em@ail.com"));
        List<VehicleReviewResponseDTO> reviews = new ArrayList<>();
        reviews.add(reviewDTO);
        VehicleReviewsDTO reviewsDTO = new VehicleReviewsDTO(12, reviews);

        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

    @PostMapping(value="/{rideId}/driver/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DriverReviewResponseDTO> saveDriverReview(@PathVariable Integer rideId,
                                                                    @PathVariable Integer id,
                                                                    @RequestBody DriverReviewRequestDTO driverReviewRequestDTO) {

        return new ResponseEntity<>(new DriverReviewResponseDTO(1, 5, "good", new UserResponseDTO(2, "em@ail.com")), HttpStatus.OK);

    }

    // Get the reviews for the specific vehicle
    @GetMapping(value = "/driver/{id}", produces = "application/json")
    public ResponseEntity<DriverReviewsDTO> getDriverReviews(@PathVariable Integer id) {

        DriverReviewResponseDTO reviewDTO = new DriverReviewResponseDTO(1, 5, "good", new UserResponseDTO(2, "em@ail.com"));
        List<DriverReviewResponseDTO> reviews = new ArrayList<>();
        reviews.add(reviewDTO);
        DriverReviewsDTO reviewsDTO = new DriverReviewsDTO(12, reviews);

        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

    // Overview of both reviews for the specific ride (driver and vehicle)
    @GetMapping(value = "/{rideId}", produces = "application/json")
    public ResponseEntity<List<RideReviewDTO>> getRideReviews(@PathVariable Integer rideId) {
        VehicleReviewResponseDTO vehicleReviewResponseDTO = new VehicleReviewResponseDTO(1, 5, "amazing", new UserResponseDTO(2, "em@ail.com"));
        DriverReviewResponseDTO driverReviewResponseDTO = new DriverReviewResponseDTO(1, 5, "good", new UserResponseDTO(2, "em@ail.com"));
        RideReviewDTO rideReviewDTO = new RideReviewDTO(vehicleReviewResponseDTO, driverReviewResponseDTO);
        List<RideReviewDTO> results = new ArrayList<>();
        results.add(rideReviewDTO);
        results.add(rideReviewDTO);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }



}
