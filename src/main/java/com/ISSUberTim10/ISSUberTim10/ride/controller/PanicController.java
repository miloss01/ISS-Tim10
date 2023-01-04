package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserExpandedDTO;
import com.ISSUberTim10.ISSUberTim10.ride.Panic;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IPanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "api/panic")
public class PanicController {

    @Autowired
    private IPanicService panicService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<PanicsDTO> getPanics() {

        List<Panic> panics = panicService.getAllPanics();

        ArrayList<PanicExpandedDTO> panicExpandedDTOs = new ArrayList<>();

        for (Panic panic : panics)
            panicExpandedDTOs.add(new PanicExpandedDTO(panic.getId().intValue(), new UserExpandedDTO(panic.getAppUser()), new RideDTO(panic.getRide()), panic.getPanicTime().toString(), panic.getReason()));

        return new ResponseEntity<>(
                new PanicsDTO(panicExpandedDTOs.size(), panicExpandedDTOs),
                HttpStatus.OK
        );

//        UserExpandedDTO user = new UserExpandedDTO(null, "Pera", "Perić", "U3dhZ2dlciByb2Nrcw==", "+381123123", "pera.peric@email.com", "Bulevar Oslobodjenja 74");
//
//        ArrayList<DepartureDestinationLocationsDTO> locations = new ArrayList<>(Arrays.asList(
//                new DepartureDestinationLocationsDTO(
//                        new LocationDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549),
//                        new LocationDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549)
//                )
//        ));
//
//        UserDTO driver = new UserDTO(123L, "user@example.com");
//
//        ArrayList<UserDTO> passengers = new ArrayList<UserDTO>(Arrays.asList(
//                new UserDTO(123L, "user@example.com")
//        ));
//
//        RejectionDTO rejection = new RejectionDTO("Ride is canceled due to previous problems with the passenger", "2022-11-25T17:32:28Z");
//
//        RideDTO ride = new RideDTO(123L, locations, "2017-07-21T17:32:28Z", "2017-07-21T17:45:14Z", 1235, driver, passengers, 5, "STANDARDNO", true, true, null, rejection);
//
//        return new ResponseEntity<>(
//                new PanicsDTO(
//                        243,
//                        new ArrayList<PanicExpandedDTO>(Arrays.asList(
//                                new PanicExpandedDTO(10, user, ride, "2022-12-05T11:26:01.591Z", "Driver is drinking while driving")
//                        ))
//                ),
//                HttpStatus.OK
//        );

    }

}
