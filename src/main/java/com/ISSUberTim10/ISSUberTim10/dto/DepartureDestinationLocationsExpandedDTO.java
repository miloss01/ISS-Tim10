package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartureDestinationLocationsExpandedDTO {

    private String address;
    private Double latitude;
    private Double longitude;
    private LocationDTO departure;
    private LocationDTO destination;

}
