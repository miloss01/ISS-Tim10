package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.VehicleDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.VehicleRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IDriverService;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service
public class DriverService implements IDriverService {
    @Autowired
    VehicleRepository vehicleRepository;

    @Override
    public ResponseEntity<VehicleDTO> getVehicle(Integer id) {
        Optional<Vehicle> found = vehicleRepository.findById(id.longValue());
        if (!found.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found to be blocked.");
        }
        Vehicle vehicle = found.get();
        VehicleDTO vehicleDTO = new VehicleDTO(vehicle);
        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VehicleDTO> saveVehicle(Integer id, VehicleDTO vehicleDTO) {
        return null;
    }

    @Override
    public ResponseEntity<VehicleDTO> updateVehicle(Integer id, VehicleDTO vehicleDTO) {
        return null;
    }
}
