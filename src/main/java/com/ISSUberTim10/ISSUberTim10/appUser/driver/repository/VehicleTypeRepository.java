package com.ISSUberTim10.ISSUberTim10.appUser.driver.repository;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {

    public VehicleType getByName(Vehicle.VEHICLE_TYPE name);

}
