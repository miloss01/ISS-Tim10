package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstimatedDataRequestDTO {

    private List<DepartureDestinationLocationsDTO> locations;
    private String vehicleType;
    private Boolean babyTransport;
    private Boolean petTransport;

}
