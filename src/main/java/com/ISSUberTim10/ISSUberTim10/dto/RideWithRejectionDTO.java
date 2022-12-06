package com.ISSUberTim10.ISSUberTim10.dto;

import java.util.ArrayList;

public class RideWithRejectionDTO extends RideWithStatusDTO{
    private RejectionDTO rejection;

    public RideWithRejectionDTO() {
        super();
    }

    public RideWithRejectionDTO(String startTime, String endTime, int totalCost, UserDTO driver, ArrayList<UserDTO> passengers, int estimatedTimeInMinutes, String vehicleType, boolean babyTransport, boolean petTransport, ArrayList<LocationDTO> locations, String status, RejectionDTO rejection) {
        super(startTime, endTime, totalCost, driver, passengers, estimatedTimeInMinutes, vehicleType, babyTransport, petTransport, locations, status);
        this.rejection = rejection;
    }

    public RejectionDTO getRejection() {
        return rejection;
    }
}
