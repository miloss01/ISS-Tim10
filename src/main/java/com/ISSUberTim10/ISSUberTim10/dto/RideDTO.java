package com.ISSUberTim10.ISSUberTim10.dto;

import java.util.ArrayList;

public class RideDTO {
    private Long id;
    private ArrayList<LocationDTO> locations;
    private String startTime;
    private String endTime;
    private int totalCost;
    private UserDTO driver;
    private ArrayList<UserDTO> passengers;
    private int estimatedTimeInMinutes;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public RideDTO() {
        this.locations = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public RideDTO(Long id, ArrayList<LocationDTO> locations, String startTime, String endTime, int totalCost, UserDTO driver, ArrayList<UserDTO> passengers, int estimatedTimeInMinutes, String vehicleType, boolean babyTransport, boolean petTransport) {
        this();
        this.id = id;
        this.locations = locations;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public Long getId() {
        return id;
    }

    public ArrayList<LocationDTO> getLocations() {
        return locations;
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
}
