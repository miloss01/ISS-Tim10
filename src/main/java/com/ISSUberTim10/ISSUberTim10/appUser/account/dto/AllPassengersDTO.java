package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AllPassengersDTO {

    private int totalCount;
    @Valid
    private List<PassengerResponseDTO> results;

}
