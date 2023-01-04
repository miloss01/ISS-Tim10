package com.ISSUberTim10.ISSUberTim10.ride.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstimatedDataRequestDTO {

    @Valid
    private List<DepartureDestinationLocationsDTO> locations;
//    @NotNull(message = "Field (vehicleType) is required!")
    private String vehicleType;
//    @NotNull(message = "Field (babyTransport) is required!")
    private Boolean babyTransport;
//    @NotNull(message = "Field (petTransport) is required!")
    private Boolean petTransport;
    @NotNull(message = "Field (distance) is required!")
    private Double distance;

}
