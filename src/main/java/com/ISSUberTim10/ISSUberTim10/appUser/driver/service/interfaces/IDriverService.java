package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;

public interface IDriverService {

    public Driver saveDriver(Driver driver);
    public Driver setVehicle(Vehicle vehicle);
    public Driver getById(Long id);

}
