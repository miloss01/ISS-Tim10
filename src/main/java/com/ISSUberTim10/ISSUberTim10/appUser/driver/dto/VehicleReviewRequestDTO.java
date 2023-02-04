package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleReviewRequestDTO {

    @NotNull(message = "Field (rating) is required!")
    @Min(value = 1, message = "Field (rating) should be between 1-5!")
    @Max(value = 5, message = "Field (rating) should be between 1-5!")
    private Integer rating;

    private String comment;
}
