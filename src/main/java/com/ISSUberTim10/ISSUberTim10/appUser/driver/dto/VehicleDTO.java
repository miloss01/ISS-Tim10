package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer driverId;
    private String vehicleType;
    private String model;
    private String licenseNumber;
    private LocationDTO currentLocation;
    private Integer passengerSeats;
    private Boolean babyTransport;
    private Boolean petTransport;

    public VehicleDTO(Vehicle vehicle) {
        this.id = Math.toIntExact(vehicle.getId());
        this.driverId = Math.toIntExact(vehicle.getDriver().getId());
        this.vehicleType = vehicle.getVehicleType().toString();
        this.model = vehicle.getModel();
        this.licenseNumber = vehicle.getRegistrationPlate();
        this.currentLocation = new LocationDTO(vehicle.getCurrentCoordinates());
        this.passengerSeats = vehicle.getNumOfSeats();
        this.babyTransport = vehicle.isBabyFlag();
        this.petTransport = vehicle.isPetsFlag();
    }
}
