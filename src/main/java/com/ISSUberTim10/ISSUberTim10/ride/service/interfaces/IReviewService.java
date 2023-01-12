package com.ISSUberTim10.ISSUberTim10.ride.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.ride.Review;

import java.util.HashMap;
import java.util.List;

public interface IReviewService {

    public Review saveVehicleReview(Review review);

    public List<Review> getVehicleReviews(Integer vehicleId);

    public Review saveDriverReview(Review review);

    public List<Review> getDriverReviews(Integer driverId);

    public HashMap<String, List<Review>> getRideReviews(Integer rideId);

}
