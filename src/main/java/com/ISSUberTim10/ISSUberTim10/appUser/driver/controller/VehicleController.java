package com.ISSUberTim10.ISSUberTim10.appUser.driver.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IVehicleService;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/vehicle")
public class VehicleController {

    @Autowired
    private IVehicleService vehicleService;

    @PutMapping(value = "/{id}/location", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateVehicle(@PathVariable Integer id,
                                              @RequestBody LocationDTO currentLocationDTO) {

        Vehicle vehicle = vehicleService.getById(id.longValue());
        vehicle.getCurrentCoordinates().setAddress(currentLocationDTO.getAddress());
        vehicle.getCurrentCoordinates().setLatitude(currentLocationDTO.getLatitude());
        vehicle.getCurrentCoordinates().setLongitude(currentLocationDTO.getLongitude());

        Vehicle saved = vehicleService.saveVehicle(vehicle);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
