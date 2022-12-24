package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserForDisplayDTO;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideWithDetailedUsersDTO {
    private Long id;
    private List<DepartureDestinationLocationsDTO> locations;
    private String startTime;
    private String endTime;
    private int totalCost;
    private UserForDisplayDTO driver;
    private List<UserForDisplayDTO> passengers;

    public RideWithDetailedUsersDTO(Ride ride) {
    List<DepartureDestinationLocationsDTO> locations = new ArrayList<>();
    List<UserForDisplayDTO> passengers = new ArrayList<>();

    this.id = ride.getId();
    this.locations = locations;
    this.startTime = ride.getStartTime().toString();
    this.endTime = ride.getEndTime().toString();
    this.totalCost = (int) ride.getPrice();
    this.driver = new UserForDisplayDTO(ride.getDriver());
    this.passengers = extractPassengerDTOs(ride);
    }

    private List<UserForDisplayDTO> extractPassengerDTOs(Ride ride) {
        List<UserForDisplayDTO> passengerDTOs = new ArrayList<>();
        for (AppUser appUser : ride.getPassengers()) {
            passengerDTOs.add(new UserForDisplayDTO(appUser));
        }
        return passengerDTOs;
    }


}
