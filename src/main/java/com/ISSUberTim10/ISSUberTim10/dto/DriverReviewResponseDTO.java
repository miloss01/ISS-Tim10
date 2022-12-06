package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class DriverReviewResponseDTO extends ReviewResponseDTO {

    public DriverReviewResponseDTO(int id, int rating, String comment, UserResponseDTO passenger) {
        super(id, rating, comment, passenger);
    }
}
