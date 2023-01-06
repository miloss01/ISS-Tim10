package com.ISSUberTim10.ISSUberTim10.appUser.driver.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IVehicleService;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/vehicle")
@CrossOrigin
@Validated
public class VehicleController {

    @Autowired
    private IVehicleService vehicleService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PutMapping(value = "/{id}/location", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize(value = "hasRole('DRIVER')")
    public ResponseEntity<Void> updateVehicle(@PathVariable Integer id,
                                              @Valid @RequestBody LocationDTO currentLocationDTO) {

        Vehicle vehicle = vehicleService.getById(id.longValue());
        vehicle.getCurrentCoordinates().setAddress(currentLocationDTO.getAddress());
        vehicle.getCurrentCoordinates().setLatitude(currentLocationDTO.getLatitude());
        vehicle.getCurrentCoordinates().setLongitude(currentLocationDTO.getLongitude());

        Vehicle saved = vehicleService.saveVehicle(vehicle);

        this.simpMessagingTemplate.convertAndSend("/vehicle-location", currentLocationDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
