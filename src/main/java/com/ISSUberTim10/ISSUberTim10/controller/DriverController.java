package com.ISSUberTim10.ISSUberTim10.controller;

import com.ISSUberTim10.ISSUberTim10.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "api/driver")
public class DriverController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> saveDriver(@RequestBody DriverRequestDTO driverDTO) {
        return new ResponseEntity<>(
                new DriverResponseDTO(123, "Pera", "Perić", "U3dhZ2dlciByb2Nrcw==", "+381123123", "pera.peric@email.com\"", "Bulevar Oslobodjenja 74"),
                HttpStatus.OK);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriversDTO> getDrivers(Pageable page) {
        return new ResponseEntity<DriversDTO>(
                new DriversDTO(
                        243,
                        new ArrayList<DriverResponseDTO>(
                                Arrays.asList(
                                        new DriverResponseDTO(123, "Pera", "Perić", "U3dhZ2dlciByb2Nrcw==", "+381123123", "pera.peric@email.com\"", "Bulevar Oslobodjenja 74")
                                )
                        )
                ),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> getDriver(@PathVariable Integer id) {
        return new ResponseEntity<>(
                new DriverResponseDTO(123, "Pera", "Perić", "U3dhZ2dlciByb2Nrcw==", "+381123123", "pera.peric@email.com\"", "Bulevar Oslobodjenja 74"),
                HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> updateDriver(@PathVariable Integer id,
                                                          @RequestBody DriverResponseDTO driverDTO) {
        return new ResponseEntity<>(
                new DriverResponseDTO(123, "Pera", "Perić", "U3dhZ2dlciByb2Nrcw==", "+381123123", "pera.peric@email.com\"", "Bulevar Oslobodjenja 74"),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentResponseDTO>> getDocuments(@PathVariable Integer id) {
        return new ResponseEntity<>(
                new ArrayList<DocumentResponseDTO>(
                        Arrays.asList(
                                new DocumentResponseDTO(123, "Vozačka dozvola", "U3dhZ2dlciByb2Nrcw=", 10)
                        )
                ),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/documents")
    public ResponseEntity<Void> deleteDocument(@PathVariable Integer id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentResponseDTO> updateDocument(@PathVariable Integer id,
                                                              @RequestBody DocumentRequestDTO documentDTO) {
        return new ResponseEntity<>(
                new DocumentResponseDTO(123, "Vozačka dozvola", "U3dhZ2dlciByb2Nrcw=", 10),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleResponseDTO> getVehicle(@PathVariable Integer id) {
        return new ResponseEntity<>(
                new VehicleResponseDTO(123, 123, "STANDARDNO", "VW Golf 2", "NS 123-AB", new CurrentLocationDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549), 4, true, true),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleResponseDTO> saveVehicle(@PathVariable Integer id,
                                                          @RequestBody VehicleRequestDTO vehicleDTO) {
        return new ResponseEntity<>(
                new VehicleResponseDTO(123, 123, "STANDARDNO", "VW Golf 2", "NS 123-AB", new CurrentLocationDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549), 4, true, true),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleResponseDTO> updateVehicle(@PathVariable Integer id,
                                                            @RequestBody VehicleRequestDTO vehicleDTO) {
        return new ResponseEntity<>(
                new VehicleResponseDTO(123, 123, "STANDARDNO", "VW Golf 2", "NS 123-AB", new CurrentLocationDTO("Bulevar oslobodjenja 46", 45.267136, 19.833549), 4, true, true),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/{id}/working-hours", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkingHoursDTO> getWorkingHours(@PathVariable Integer id, Pageable page,
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

    @PostMapping(value = "/{id}/working-hours", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkingHourDTO> saveWorkingHour(@PathVariable Integer id) {
        return new ResponseEntity<>(
                new WorkingHourDTO(10, "2022-12-04T11:51:29.756Z", "2022-12-04T11:51:29.756Z"),
                HttpStatus.OK
        );
    }

    // TODO - add /api/driver/{id}/ride, depends on ride

    @GetMapping(value = "/{driver-id}/working-hour/{working-hour-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkingHourDTO> getWorkingHour(@PathVariable(name = "driver-id") Integer driverId,
                                                         @PathVariable(name = "working-hour-id") Integer workingHourId) {
        return new ResponseEntity<>(
                new WorkingHourDTO(10, "2022-12-04T11:51:29.756Z", "2022-12-04T11:51:29.756Z"),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/{driver-id}/working-hour/{working-hour-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkingHourDTO> updateWorkingHour(@PathVariable(name = "driver-id") Integer driverId,
                                                            @PathVariable(name = "working-hour-id") Integer workingHourId) {
        return new ResponseEntity<>(
                new WorkingHourDTO(10, "2022-12-04T11:51:29.756Z", "2022-12-04T11:51:29.756Z"),
                HttpStatus.OK
        );
    }

}
