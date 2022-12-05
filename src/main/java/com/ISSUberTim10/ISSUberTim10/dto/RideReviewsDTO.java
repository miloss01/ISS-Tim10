package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideReviewsDTO {

    private VehicleReviewResponseDTO vehicleReview;

    private DriverReviewResponseDTO driverReview;

}
