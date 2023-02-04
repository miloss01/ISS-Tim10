package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ReviewResponseDTO {

    @NotNull(message = "Field (id) is required!")
    private Integer id;

    private Integer rating;

    private String comment;

    @Valid
    private UserResponseDTO passenger;

}
