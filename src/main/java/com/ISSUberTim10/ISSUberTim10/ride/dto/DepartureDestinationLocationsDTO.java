package com.ISSUberTim10.ISSUberTim10.ride.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepartureDestinationLocationsDTO {

    private LocationDTO departure;
    private LocationDTO destination;

}
