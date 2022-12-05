package com.ISSUberTim10.ISSUberTim10.controller;

import com.ISSUberTim10.ISSUberTim10.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewContoller {

    // Creating a review about the vehicle
    @PostMapping(value = "/vehicle/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<VehicleReviewResponseDTO> saveVehicleReview(@PathVariable Integer id,
                                                               @RequestBody VehicleReviewRequestDTO vehicleReviewRequestDTO) {
        return new ResponseEntity<>(new VehicleReviewResponseDTO(1, 5, "amazing"), HttpStatus.OK);
    }


    // Get the reviews for the specific vehicle
    @GetMapping(value = "/vehicle/{id}", consumes = "application/json")
    public ResponseEntity<VehicleReviewsDTO> getVehicleReviews(@PathVariable Integer id) {

        VehicleReviewResponseDTO reviewDTO = new VehicleReviewResponseDTO(1, 5, "amazing");
        List<VehicleReviewResponseDTO> reviews = new ArrayList<>();
        reviews.add(reviewDTO);
        VehicleReviewsDTO reviewsDTO = new VehicleReviewsDTO(12, reviews);

        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

    @PostMapping(value="/driver/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DriverReviewResponseDTO> saveDriverReview(@PathVariable Integer id,
                                                                    @RequestBody DriverReviewRequestDTO driverReviewRequestDTO) {

        return new ResponseEntity<>(new DriverReviewResponseDTO(1, 5, "good"), HttpStatus.OK);

    }

    // Get the reviews for the specific vehicle
    @GetMapping(value = "/driver/{id}", consumes = "application/json")
    public ResponseEntity<DriverReviewsDTO> getDriverReviews(@PathVariable Integer id) {

        DriverReviewResponseDTO reviewDTO = new DriverReviewResponseDTO(1, 5, "good");
        List<DriverReviewResponseDTO> reviews = new ArrayList<>();
        reviews.add(reviewDTO);
        DriverReviewsDTO reviewsDTO = new DriverReviewsDTO(12, reviews);

        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

    // Overview of both reviews for the specific ride (driver and vehicle)
    @GetMapping(value = "/{rideId}", produces = "application/json")
    public ResponseEntity<RideReviewsDTO> getRideReviews(@PathVariable Integer rideId) {
        return new ResponseEntity<>(new RideReviewsDTO(new VehicleReviewResponseDTO(1, 5, "amazing"),
                new DriverReviewResponseDTO(1, 5, "good")), HttpStatus.OK);
    }



}
