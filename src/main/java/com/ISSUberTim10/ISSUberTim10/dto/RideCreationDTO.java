package com.ISSUberTim10.ISSUberTim10.dto;

import java.util.ArrayList;

public class RideCreationDTO {
    private ArrayList<LocationForRideDTO> locations;
    private ArrayList<UserDTO> passengers;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public RideCreationDTO() {
        this.locations = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public RideCreationDTO(ArrayList<LocationForRideDTO> locations, ArrayList<UserDTO> passengers, String vehicleType, boolean babyTransport, boolean petTransport) {
        this();
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public ArrayList<LocationForRideDTO> getLocations() {
        return locations;
    }

    public ArrayList<UserDTO> getPassengers() {
        return passengers;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public boolean isBabyTransport() {
        return babyTransport;
    }

    public boolean isPetTransport() {
        return petTransport;
    }
}
