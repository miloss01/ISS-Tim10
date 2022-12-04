package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequestDTO {

    private String vehicleType;
    private String model;
    private String licenseNumber;
    private LocationDTO currentLocationDTO;
    private Integer passengerSeats;
    private Boolean babyTransport;
    private Boolean petTransport;

}
