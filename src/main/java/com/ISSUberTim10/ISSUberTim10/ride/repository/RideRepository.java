package com.ISSUberTim10.ISSUberTim10.ride.repository;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {



    public Page<Ride> findAll(Pageable pageable);
    public Page findAllByDriver(Pageable pageable, Driver driver);
    public Page findAllByPassengersContaining(Pageable pageable, Passenger passenger);


    ArrayList<Ride> findAllByStartTimeGreaterThanEqualOrEndTimeLessThanEqual(LocalDateTime minusHours, LocalDateTime plusHours);
}