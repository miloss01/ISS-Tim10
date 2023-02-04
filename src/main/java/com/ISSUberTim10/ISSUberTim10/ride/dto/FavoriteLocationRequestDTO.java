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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FavoriteLocationRequestDTO {

    @NotNull(message = "Field (favorite name) is required!")
    @NotEmpty(message = "Field (favorite name) is required!")
    @Size(max = 40, message = "Field (favorite name) cannot be longer than 100 characters!")
    private String favoriteName;

    @NotNull(message = "Field (locations) is required!")
    @Valid
    private List<DepartureDestinationLocationsDTO> locations;
    @Valid
    private List<UserResponseDTO> passengers;

    @Pattern(regexp = "standard|luxury|van", message="Field (vehicle type) has incorrect value!")
    @Valid
    private String vehicleType;
    @NotNull(message = "Field (babyTransport) is required!")
    private boolean babyTransport;
    @NotNull(message = "Field (petTransport) is required!")
    private boolean petTransport;

}
