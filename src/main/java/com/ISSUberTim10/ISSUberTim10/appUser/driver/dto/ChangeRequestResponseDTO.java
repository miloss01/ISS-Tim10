package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.ArrayList;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequestResponseDTO {
    private int numberOfRequests;
    @Valid
    private ArrayList<ChangeRequestDTO> requestDTOS;
}
