package com.ISSUberTim10.ISSUberTim10.controller;

import com.ISSUberTim10.ISSUberTim10.dto.LocationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/vehicle")
public class VehicleController {

    @PutMapping(value = "/{id}/location", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateVehicle(@PathVariable Integer id,
                                              @RequestBody LocationDTO currentLocationDTO) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
