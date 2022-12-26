package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.VehicleType;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.VehicleTypeRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IVehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleTypeService implements IVehicleTypeService {

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Override
    public VehicleType getByName(Vehicle.VEHICLE_TYPE name) {
        return vehicleTypeRepository.getByName(name);
    }

}
