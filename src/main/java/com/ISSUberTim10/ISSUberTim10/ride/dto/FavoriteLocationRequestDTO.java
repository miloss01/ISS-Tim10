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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FavoriteLocationRequestDTO {

    @NotNull(message = "Field (favorite name) is required!")
    @Valid
    private String favoriteName;

    @NotNull(message = "Field (locations) is required!")
    @Valid
    private List<DepartureDestinationLocationsDTO> locations;

    private List<UserResponseDTO> passengers;

    @Pattern(regexp = "standard|luxury|van", message="Field (vehicle type) has incorrect value!")
    @Valid
    private String vehicleType;

    private boolean babyTransport;

    private boolean petTransport;

}
