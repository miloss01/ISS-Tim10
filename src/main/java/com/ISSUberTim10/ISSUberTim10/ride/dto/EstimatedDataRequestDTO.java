package com.ISSUberTim10.ISSUberTim10.ride.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstimatedDataRequestDTO {

    private List<DepartureDestinationLocationsDTO> locations;
    private String vehicleType;
    private Boolean babyTransport;
    private Boolean petTransport;

}
