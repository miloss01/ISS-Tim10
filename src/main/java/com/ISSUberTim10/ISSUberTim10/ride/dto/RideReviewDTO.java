package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.DriverReviewResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.VehicleReviewResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideReviewDTO {

    private VehicleReviewResponseDTO vehicleReview;

    private DriverReviewResponseDTO driverReview;

}
