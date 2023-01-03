package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer driverId;
    @NotNull(message = "Field (vehicleType) is required!")
    private String vehicleType;
    @NotBlank(message = "Field (model) is required!")
    @Size(max = 100, message = "Field (model) cannot be longer than 100 characters!")
    private String model;
    @NotBlank(message = "Field (licenseNumber) is required!")
    @Size(max = 20, message = "Field (licenseNumber) cannot be longer than 20 characters!")
    private String licenseNumber;
    private LocationDTO currentLocation;
    @NotNull(message = "Field (passengerSeats) is required!")
    @Min(value = 1, message = "Field (passengerSeats) cannot be smaller than 1!")
    @Max(value = 20, message = "Field (passengerSeats) cannot be bigger than 20!")
    private Integer passengerSeats;
    private Boolean babyTransport;
    private Boolean petTransport;

    public VehicleDTO(Vehicle vehicle) {
        this.id = Math.toIntExact(vehicle.getId());
        this.driverId = Math.toIntExact(vehicle.getDriver().getId());
        this.vehicleType = vehicle.getVehicleType().getName().toString();
        this.model = vehicle.getModel();
        this.licenseNumber = vehicle.getRegistrationPlate();
        this.currentLocation = new LocationDTO(vehicle.getCurrentCoordinates());
        this.passengerSeats = vehicle.getNumOfSeats();
        this.babyTransport = vehicle.isBabyFlag();
        this.petTransport = vehicle.isPetsFlag();
    }
}
