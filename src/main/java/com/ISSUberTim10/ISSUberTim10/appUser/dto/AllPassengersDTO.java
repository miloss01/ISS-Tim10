package com.ISSUberTim10.ISSUberTim10.appUser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AllPassengersDTO {

    private int totalCount;

    private List<PassengerResponseDTO> results;

}
