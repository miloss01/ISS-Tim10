package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.ride.Coordinates;
import com.ISSUberTim10.ISSUberTim10.ride.Route;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LocationDTO {

    private String address;

    private double latitude;

    private double longitude;

    public LocationDTO(Coordinates currentCoordinates) {
        this.address = currentCoordinates.getAddress();
        this.latitude = currentCoordinates.getLatitude();
        this.longitude = currentCoordinates.getLongitude();
    }


}
