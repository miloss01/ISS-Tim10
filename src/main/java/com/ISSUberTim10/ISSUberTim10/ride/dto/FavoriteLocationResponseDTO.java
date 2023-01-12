package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FavoriteLocationResponseDTO {

    public Long id;

    private String favoriteName;

    private List<DepartureDestinationLocationsDTO> locations;

    private List<UserResponseDTO> passengers;

    private String vehicleType;

    private boolean babyTransport;

    private boolean petTransport;

}
