package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import com.ISSUberTim10.ISSUberTim10.ride.dto.ReviewResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.dto.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverReviewResponseDTO extends ReviewResponseDTO {

    public DriverReviewResponseDTO(int id, int rating, String comment, UserResponseDTO passenger) {
        super(id, rating, comment, passenger);
    }
}
