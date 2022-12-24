package com.ISSUberTim10.ISSUberTim10.ride.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RideWithDetailedUsersResponseDTO {

    private int totalCount;
    private List<RideWithDetailedUsersDTO> results;

    public RideWithDetailedUsersResponseDTO() {
        this.results = new ArrayList<>();
    }

    public RideWithDetailedUsersResponseDTO(int totalCount, List<RideWithDetailedUsersDTO> results) {
        this();
        this.totalCount = totalCount;
        this.results = results;
    }
}
