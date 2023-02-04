package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.ride.Coordinates;
import com.ISSUberTim10.ISSUberTim10.ride.Location;
import com.ISSUberTim10.ISSUberTim10.ride.Route;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LocationDTO {

    private String address;
    @NotNull(message = "Field (latitude) is required!")
    @Min(value = -90, message = "Field (latitude) cannot be smaller than -90!")
    @Max(value = 90, message = "Field (latitude) cannot be bigger than 90!")
    private Double latitude;
    @NotNull(message = "Field (longitude) is required!")
    @Min(value = -180, message = "Field (longitude) cannot be smaller than -180!")
    @Max(value = 180, message = "Field (longitude) cannot be bigger than 180!")
    private Double longitude;

    public LocationDTO(Coordinates currentCoordinates) {
        this.address = currentCoordinates.getAddress();
        this.latitude = currentCoordinates.getLatitude();
        this.longitude = currentCoordinates.getLongitude();
    }

    public LocationDTO(Location location) {
        this.address = location.getAddress();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }


}
