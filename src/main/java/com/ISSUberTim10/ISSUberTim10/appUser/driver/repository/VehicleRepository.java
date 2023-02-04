package com.ISSUberTim10.ISSUberTim10.appUser.driver.repository;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    ArrayList<Vehicle> findByVehicleType(VehicleType id);
}
