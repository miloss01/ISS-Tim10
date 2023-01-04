package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverReviewsDTO {

    private Integer totalCount;

    @Valid
    private List<DriverReviewResponseDTO> results;

}