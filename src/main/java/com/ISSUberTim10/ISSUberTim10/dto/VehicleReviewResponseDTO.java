package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.*;

@Getter
@Setter
public class VehicleReviewResponseDTO extends ReviewResponseDTO {

    public VehicleReviewResponseDTO(Integer id , Integer rating, String comment, UserResponseDTO passenger) {
        super(id, rating, comment, passenger);
    }
}

