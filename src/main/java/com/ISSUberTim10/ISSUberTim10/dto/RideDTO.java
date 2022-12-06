package com.ISSUberTim10.ISSUberTim10.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideDTO {
    private Long id;
    private ArrayList<DepartureDestinationLocationsDTO> locations;
    private String startTime;
    private String endTime;
    private int totalCost;
    private UserDTO driver;
    private ArrayList<UserDTO> passengers;
    private int estimatedTimeInMinutes;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    @JsonInclude(JsonInclude.Include. NON_NULL)
    private String status;

    private RejectionDTO rejection;


}
