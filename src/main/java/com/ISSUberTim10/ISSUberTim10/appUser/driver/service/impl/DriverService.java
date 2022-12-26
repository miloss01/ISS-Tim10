package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.DriverRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService implements IDriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Override
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public Driver setVehicle(Vehicle vehicle) {
        Driver driver = driverRepository.getById(vehicle.getDriver().getId());
        driver.setVehicle(vehicle);
        return driverRepository.save(driver);
    }

    @Override
    public Driver getById(Long id) {
        return driverRepository.getById(id);
    }

}
