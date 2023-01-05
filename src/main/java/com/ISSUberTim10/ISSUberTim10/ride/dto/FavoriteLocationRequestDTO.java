package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.VehicleType;
import com.ISSUberTim10.ISSUberTim10.ride.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FavoriteLocationRequestDTO {

    private String favoriteName;

    private List<DepartureDestinationLocationsDTO> locations;

    private List<UserResponseDTO> passengers;

    private String vehicleType;

    private boolean babyTransport;

    private boolean petTransport;

}
