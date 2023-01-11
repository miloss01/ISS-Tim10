package com.ISSUberTim10.ISSUberTim10.ride.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.PanicExpandedDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.ReasonDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface IRideService {
    public Collection<Ride> getAll();

    public void createAll();

    public void deleteAll();

    public Page<Ride> getByUser(Long id, Pageable page);
    public Ride getRideById(Long id);
    public List<Ride> getByDriver(Pageable pageable, Driver driver);
    public List<Ride> getByPassenger(Pageable pageable, Passenger passenger);

//    public ArrayList<Ride> getByDriverAndStatus(Driver driver, ArrayList<Ride.RIDE_STATUS> statuses);
//    public Ride getByPassengerAndStatus(Passenger passenger, ArrayList<Ride.RIDE_STATUS> statuses);
//    ResponseEntity<RideDTO> cancelRide(Integer id);
//    ResponseEntity<PanicExpandedDTO> addPanic(Integer id, ReasonDTO panic);

    public Ride getByDriverAndStatus(Driver driver, Ride.RIDE_STATUS status);
    public Ride getByPassengerAndStatus(Passenger passenger, Ride.RIDE_STATUS status);

    Ride acceptRide(Ride id);
    Ride endRide(Ride id);
    Ride cancelRideWithExplanation(Ride id, String reason);

    boolean isBookableRide(Ride newRideRequest);

    void save(Ride newRideRequest);

    Ride withdrawRide(Ride ride);

    Ride startRide(Ride ride);
}
