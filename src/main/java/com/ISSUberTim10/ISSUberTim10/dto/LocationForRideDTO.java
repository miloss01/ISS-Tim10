package com.ISSUberTim10.ISSUberTim10.dto;

public class LocationForRideDTO {
    private LocationDTO departure;
    private LocationDTO destination;

    public LocationForRideDTO() {
    }

    public LocationForRideDTO(LocationDTO departure, LocationDTO destination) {
        this.departure = departure;
        this.destination = destination;
    }

    public LocationDTO getDeparture() {
        return departure;
    }

    public LocationDTO getDestination() {
        return destination;
    }
}
