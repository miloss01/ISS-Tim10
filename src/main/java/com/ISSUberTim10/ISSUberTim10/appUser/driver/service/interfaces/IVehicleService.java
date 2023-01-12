package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces;


import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.VehicleDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


public interface IVehicleService {
    public Vehicle saveVehicle(Vehicle vehicle);
    public Vehicle getById(Long id);
    ArrayList<Vehicle> getAllVehicles();

    boolean IsVehicleInActiveRide(Driver driver);
}
