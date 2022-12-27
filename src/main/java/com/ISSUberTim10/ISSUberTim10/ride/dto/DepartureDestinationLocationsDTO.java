package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.ride.Route;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepartureDestinationLocationsDTO {

    private LocationDTO departure;
    private LocationDTO destination;

    public DepartureDestinationLocationsDTO(Route route) {
        this.departure = new LocationDTO(route.getDepartureCoordinates());
        this.destination = new LocationDTO(route.getDestinationCoordinates());
    }
}
