package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.Valid;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleForMapDTO {
    @Valid
    private LocationDTO currentLocation;
    boolean active;

    public VehicleForMapDTO(Vehicle vehicle) {
        this.currentLocation = new LocationDTO(vehicle.getCurrentCoordinates());
        this.active = vehicle.getDriver().isActiveFlag();
    }
}
