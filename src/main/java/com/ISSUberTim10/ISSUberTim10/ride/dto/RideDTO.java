package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.Route;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private ArrayList<DepartureDestinationLocationsDTO> locations;
    private String startTime;
    private String endTime;
    private int totalCost;
    private UserDTO driver;
    private ArrayList<UserDTO> passengers;
    private int estimatedTimeInMinutes;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    @JsonInclude(JsonInclude.Include. NON_NULL)
    private String status;

    private RejectionDTO rejection;


    public RideDTO(Ride ride) {
        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
        for (Route route: ride.getRoutes()) {
            locations.add(new DepartureDestinationLocationsDTO(route));
        }
        ArrayList<UserDTO> passengers = new ArrayList<>();
        for (Passenger passenger: ride.getPassengers()) {
            passengers.add(new UserDTO(passenger));
        }
        this.id = ride.getId();
        this.locations = locations;
        this.startTime = ride.getStartTime().toString();
        this.endTime = ride.getEndTime().toString();
        this.totalCost = (int) ride.getPrice();
        this.driver = new UserDTO(ride.getDriver());
        this.passengers = passengers;
        this.estimatedTimeInMinutes = ride.getEstimatedTimeMinutes();
        this.vehicleType = ride.getVehicleType();
        this.babyTransport = ride.isBabyTransport();
        this.petTransport = ride.isPetTransport();
        this.status = ride.getRideStatus().toString();
        this.rejection = new RejectionDTO(ride.getRejection());
    }
}
