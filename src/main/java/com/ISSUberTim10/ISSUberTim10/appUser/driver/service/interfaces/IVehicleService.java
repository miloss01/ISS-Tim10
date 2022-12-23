package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.VehicleDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IVehicleService {
    public ResponseEntity<Void> updateVehicle( Integer id, LocationDTO currentLocationDTO);

}
