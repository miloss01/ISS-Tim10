package com.ISSUberTim10.ISSUberTim10.ride.service.impl;

import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.ride.Review;
import com.ISSUberTim10.ISSUberTim10.ride.repository.ReviewRepository;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IReviewService;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public Review saveVehicleReview(Review review) {
        if (review.getRide().getEndTime().isBefore(LocalDateTime.now().minusDays(3))) {
            throw new CustomException("Review can be left only up to 3 days after ride has ended.",
                    HttpStatus.FORBIDDEN);
        }
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getVehicleReviews(Integer vehicleId) {
        List<Review> vehicleReviews = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAll();
        for (Review review : reviews) {
            if (review.getRide().getDriver().getVehicle().getId() == vehicleId.longValue()
                    && !review.isForDriver()) {
                vehicleReviews.add(review);
            }
        }
        return vehicleReviews;
    }

    @Override
    public Review saveDriverReview(Review review) {
        if (review.getRide().getEndTime().isBefore(LocalDateTime.now().minusDays(3))) {
            throw new CustomException("Review can be left only up to 3 days after ride has ended.",
                    HttpStatus.FORBIDDEN);
        }
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getDriverReviews(Integer driverId) {
        List<Review> driverReviews = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAll();
        for (Review review : reviews) {
            if (review.getRide().getDriver().getId() == driverId.longValue()
            && review.isForDriver()) {
                driverReviews.add(review);
            }
        }
        return driverReviews;
    }

    @Override
    public HashMap<String, List<Review>> getRideReviews(Integer rideId) {
        HashMap<String, List<Review>> reviewsMap = new HashMap<>();
        List<Review> reviews = reviewRepository.findAll();
        for (Review review : reviews) {
            if (review.getRide().getId() == rideId.longValue()) {
                if (reviewsMap.containsKey(review.getPassenger().getEmail())) {
                    reviewsMap.get(review.getPassenger().getEmail()).add(review);
                } else {
                    List<Review> reviewList = new ArrayList<>();
                    reviewList.add(review);
                    reviewsMap.put(review.getPassenger().getEmail(), reviewList);
                }
            }
        }
        return reviewsMap;
    }
}
