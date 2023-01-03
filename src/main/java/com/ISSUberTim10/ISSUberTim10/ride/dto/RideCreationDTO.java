package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
@Getter
@Setter
public class RideCreationDTO {
    @NotNull(message = "Field (locations) is required!")
    private ArrayList<DepartureDestinationLocationsDTO> locations;
    @NotNull(message = "Field (passengers) is required!")
    private ArrayList<UserDTO> passengers;
    @NotBlank(message = "Field (vehicleType) is required!")
    @Size(max = 50, message = "Field (vehicleType) cannot be longer than 50 characters!")
    private String vehicleType;
    @NotNull(message = "Field (babyTransport) is required!")
    private boolean babyTransport;
    @NotNull(message = "Field (petTransport) is required!")
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
