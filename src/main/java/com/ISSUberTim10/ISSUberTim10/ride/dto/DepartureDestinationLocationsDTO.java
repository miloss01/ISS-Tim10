package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.ride.Route;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepartureDestinationLocationsDTO {

    @NotNull(message = "Field (departure) is required!")
    private LocationDTO departure;
    @NotNull(message = "Field (destination) is required!")
    private LocationDTO destination;

    public DepartureDestinationLocationsDTO(Route route) {
        this.departure = new LocationDTO(route.getDepartureCoordinates());
        this.destination = new LocationDTO(route.getDestinationCoordinates());
    }
}
