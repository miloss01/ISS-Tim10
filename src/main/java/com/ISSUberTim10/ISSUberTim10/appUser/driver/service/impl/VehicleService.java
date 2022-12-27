package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.VehicleRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IVehicleService;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VehicleService implements IVehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public ResponseEntity<Void> updateVehicle(Integer id, LocationDTO currentLocationDTO) {
        return null;
    }

}
