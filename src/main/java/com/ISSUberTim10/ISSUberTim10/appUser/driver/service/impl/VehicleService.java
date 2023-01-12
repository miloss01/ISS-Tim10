package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.VehicleRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IVehicleService;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class VehicleService implements IVehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    RideRepository rideRepository;

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        checkIfVehicleAssigned(vehicle);
        return vehicleRepository.save(vehicle);
    }

    private void checkIfVehicleAssigned(Vehicle vehicle) {
        try {
            vehicle.getDriver().getId();
        }catch (NullPointerException ex) {
            throw new CustomException("Vehicle is not assigned to the specific driver!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Vehicle getById(Long id) {

        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        if (!vehicle.isPresent())
            throw new CustomException("Vehicle does not exist!", HttpStatus.NOT_FOUND);

        return vehicle.get();

    }

    @Override
    public ArrayList<Vehicle> getAllVehicles() {
        return (ArrayList<Vehicle>) vehicleRepository.findAll();
    }

    @Override
    public boolean IsVehicleInActiveRide(Driver driver) {
        ArrayList<Ride.RIDE_STATUS> statuses = new ArrayList<>();
        statuses.add(Ride.RIDE_STATUS.active);
        ArrayList<Ride> rides = rideRepository.findAllByRideStatusInAndDriver(statuses, driver);
        return rides.size()>0;
    }


}
