package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import com.ISSUberTim10.ISSUberTim10.ride.dto.ReviewResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.dto.UserResponseDTO;
import lombok.*;

@Getter
@Setter
public class VehicleReviewResponseDTO extends ReviewResponseDTO {

    public VehicleReviewResponseDTO(Integer id , Integer rating, String comment, UserResponseDTO passenger) {
        super(id, rating, comment, passenger);
    }
}

