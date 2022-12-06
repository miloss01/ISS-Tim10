package com.ISSUberTim10.ISSUberTim10.dto;

import java.util.ArrayList;

public class RideWithStatusDTO {
    private String startTime;
    private String endTime;
    private int totalCost;
    private UserDTO driver;
    private ArrayList<UserDTO> passengers;
    private int estimatedTimeInMinutes;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private ArrayList<LocationDTO> locations;
    private String status;

    public RideWithStatusDTO() {
        this.locations = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public RideWithStatusDTO(String startTime, String endTime, int totalCost, UserDTO driver, ArrayList<UserDTO> passengers, int estimatedTimeInMinutes, String vehicleType, boolean babyTransport, boolean petTransport, ArrayList<LocationDTO> locations, String status) {
        this();
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.locations = locations;
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public UserDTO getDriver() {
        return driver;
    }

    public ArrayList<UserDTO> getPassengers() {
        return passengers;
    }

    public int getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
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

    public ArrayList<LocationDTO> getLocations() {
        return locations;
    }

    public String getStatus() {
        return status;
    }
}
