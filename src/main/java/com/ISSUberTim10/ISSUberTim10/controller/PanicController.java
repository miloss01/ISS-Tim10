package com.ISSUberTim10.ISSUberTim10.controller;

import com.ISSUberTim10.ISSUberTim10.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping(value = "api/panic")
public class PanicController {

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PanicsDTO> getPanics() {

        UserExpandedDTO user = new UserExpandedDTO(null, "Pera", "Perić", "U3dhZ2dlciByb2Nrcw==", "+381123123", "pera.peric@email.com", "Bulevar Oslobodjenja 74");

        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>(Arrays.asList(
                new DepartureDestinationLocationsDTO(
                        new LocationDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549),
                        new LocationDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549)
                )
        ));

        UserDTO driver = new UserDTO(123L, "user@example.com");

        ArrayList<UserDTO> passengers = new ArrayList<UserDTO>(Arrays.asList(
                new UserDTO(123L, "user@example.com")
        ));

        RejectionDTO rejection = new RejectionDTO("Ride is canceled due to previous problems with the passenger", "2022-11-25T17:32:28Z");

        RideDTO ride = new RideDTO(123L, locations, "2017-07-21T17:32:28Z", "2017-07-21T17:45:14Z", 1235, driver, passengers, 5, "STANDARDNO", true, true, null, rejection);

        return new ResponseEntity<>(
                new PanicsDTO(
                        243,
                        new ArrayList<PanicExpandedDTO>(Arrays.asList(
                                new PanicExpandedDTO(10, user, ride, "2022-12-05T11:26:01.591Z", "Driver is drinking while driving")
                        ))
                ),
                HttpStatus.OK
        );

    }

}
