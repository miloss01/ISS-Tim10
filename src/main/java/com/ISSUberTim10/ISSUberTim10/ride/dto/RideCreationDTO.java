package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class RideCreationDTO {
    private ArrayList<DepartureDestinationLocationsDTO> locations;
    private ArrayList<UserDTO> passengers;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public RideCreationDTO() {
        this.locations = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public RideCreationDTO(ArrayList<DepartureDestinationLocationsDTO> locations, ArrayList<UserDTO> passengers, String vehicleType, boolean babyTransport, boolean petTransport) {
        this();
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }


}
