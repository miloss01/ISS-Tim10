package com.ISSUberTim10.ISSUberTim10.dto;

import com.ISSUberTim10.ISSUberTim10.domain.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PassengerRideDTO {

    private long id;

    private List<LocationDTO> locations;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double totalCost;

    private UserResponseDTO driver;

    private List<UserResponseDTO> passengers;

    private int estimatedTimeInMinutes;

    private Vehicle.VEHICLE_TYPE vehicleType;

    private boolean babyTransport;

    private boolean petTransport;



}
