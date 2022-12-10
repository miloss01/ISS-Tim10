package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class RideResponseDTO {
    private int totalCount;
    private ArrayList<RideDTO> results;

    public RideResponseDTO() {
        this.results = new ArrayList<>();
    }

    public RideResponseDTO(int totalCount, ArrayList<RideDTO> results) {
        this();
        this.totalCount = totalCount;
        this.results = results;
    }

}
