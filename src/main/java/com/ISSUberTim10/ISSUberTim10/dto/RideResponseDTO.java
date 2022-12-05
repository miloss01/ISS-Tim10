package com.ISSUberTim10.ISSUberTim10.dto;

import java.util.ArrayList;

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

    public int getTotalCount() {
        return totalCount;
    }

    public ArrayList<RideDTO> getResults() {
        return results;
    }
}
