package com.ISSUberTim10.ISSUberTim10.appUser.driver.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.*;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/driver")
public class DriverController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverDTO> saveDriver(@RequestBody DriverDTO driverDTO) {
        return new ResponseEntity<>(
                new DriverDTO(123, "Pera", "Perić", "U3dhZ2dlciByb2Nrcw==", "+381123123", "pera.peric@email.com\"", "Bulevar Oslobodjenja 74", null),
                HttpStatus.OK);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriversDTO> getDrivers(Pageable page) {
        return new ResponseEntity<DriversDTO>(
                new DriversDTO(
                        243,
                        new ArrayList<DriverDTO>(
                                Arrays.asList(
                                        new DriverDTO(1, "Pera", "Perić", "U3dhZ2dlciByb2Nrcw==", "+381123123", "pera.peric@email.com\"", "Bulevar Oslobodjenja 74", null)
                                )
                        )
                ),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverDTO> getDriver(@PathVariable Integer id) {
        return new ResponseEntity<>(
                new DriverDTO(123, "Pera", "Perić", "U3dhZ2dlciByb2Nrcw==", "+381123123", "pera.peric@email.com\"", "Bulevar Oslobodjenja 74", null),
                HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverDTO> updateDriver(@PathVariable Integer id,
                                                  @RequestBody DriverDTO driverDTO) {
        return new ResponseEntity<>(
                new DriverDTO(123, "Pera", "Perić", "U3dhZ2dlciByb2Nrcw==", "+381123123", "pera.peric@email.com\"", "Bulevar Oslobodjenja 74", null),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentDTO>> getDocuments(@PathVariable Integer id) {
        return new ResponseEntity<>(
                new ArrayList<DocumentDTO>(
                        Arrays.asList(
                                new DocumentDTO(123, "Vozačka dozvola", "U3dhZ2dlciByb2Nrcw=", 10),
                                new DocumentDTO(123, "Vozačka dozvola", "U3dhZ2dlciByb2Nrcw=", 10)
                        )
                ),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/document/{document-id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable(name = "document-id") Integer documentId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{id}/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentDTO> updateDocument(@PathVariable Integer id,
                                                      @RequestBody DocumentDTO documentDTO) {
        return new ResponseEntity<>(
                new DocumentDTO(123, "Vozačka dozvola", "U3dhZ2dlciByb2Nrcw=", 10),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Integer id) {
        return new ResponseEntity<>(
                new VehicleDTO(123, 123, "STANDARDNO", "VW Golf 2", "NS 123-AB", new LocationDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549), 4, false, true),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> saveVehicle(@PathVariable Integer id,
                                                  @RequestBody VehicleDTO vehicleDTO) {
        return new ResponseEntity<>(
                new VehicleDTO(123, 123, "STANDARDNO", "VW Golf 2", "NS 123-AB", new LocationDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549), 4, true, true),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Integer id,
                                                    @RequestBody VehicleDTO vehicleDTO) {
        return new ResponseEntity<>(
                new VehicleDTO(123, 123, "STANDARDNO", "VW Golf 2", "NS 123-AB", new LocationDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549), 4, true, true),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/{id}/working-hour", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkingHoursDTO> getWorkingHours(@PathVariable Integer id,
                                                           Pageable page,
                                                           @RequestParam(required = false) String from,
                                                           @RequestParam(required = false) String to) {
        return new ResponseEntity<WorkingHoursDTO>(
                new WorkingHoursDTO(
                        243,
                        new ArrayList<WorkingHourDTO>(
                                Arrays.asList(
                                        new WorkingHourDTO(10, "2022-12-04T11:51:29.756Z", "2022-12-04T11:51:29.756Z")
                                )
                        )
                ),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/{id}/working-hour", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkingHourDTO> saveWorkingHour(@PathVariable Integer id,
                                                          @RequestBody WorkingHourDTO workingHourDTO) {
        return new ResponseEntity<>(
                new WorkingHourDTO(10, "2022-12-04T11:51:29.756Z", "2022-12-04T11:51:29.756Z"),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> getDriversRide(@PathVariable Integer id,
                                                          Pageable page,
                                                          @RequestParam(required = false) String from,
                                                          @RequestParam(required = false) String to) {

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
                new RideResponseDTO(
                        243,
                        new ArrayList<RideDTO>(
                                Arrays.asList(ride)
                        )
                ),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/working-hour/{working-hour-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkingHourDTO> getWorkingHour(@PathVariable(name = "working-hour-id") Integer workingHourId) {
        return new ResponseEntity<>(
                new WorkingHourDTO(10, "2022-12-04T11:51:29.756Z", "2022-12-04T11:51:29.756Z"),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/working-hour/{working-hour-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkingHourDTO> updateWorkingHour(@PathVariable(name = "working-hour-id") Integer workingHourId,
                                                            @RequestBody WorkingHourDTO workingHourDTO) {
        return new ResponseEntity<>(
                new WorkingHourDTO(10, "2022-12-04T11:51:29.756Z", "2022-12-04T11:51:29.756Z"),
                HttpStatus.OK
        );
    }

}
