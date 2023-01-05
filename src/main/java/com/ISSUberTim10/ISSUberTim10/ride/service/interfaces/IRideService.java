package com.ISSUberTim10.ISSUberTim10.ride.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.PanicExpandedDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.ReasonDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideCreationDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

public interface IRideService {
    public Collection<Ride> getAll();

    public void createAll();

    public void deleteAll();

    public Page<Ride> getByUser(Long id, Pageable page);
    ResponseEntity<RideDTO> addRide(RideCreationDTO rideCreation);
    ResponseEntity<RideDTO> getRideByDriverId(Integer driverId);
    ResponseEntity<RideDTO> getRideById(Integer id);
    public Ride getRideById(Long id);
    public List<Ride> getByDriver(Pageable pageable, Driver driver);
    public List<Ride> getByPassenger(Pageable pageable, Passenger passenger);
    public Ride getByDriverAndStatus(Driver driver, Ride.RIDE_STATUS status);
    public Ride getByPassengerAndStatus(Passenger passenger, Ride.RIDE_STATUS status);
    ResponseEntity<RideDTO> cancelRide(Integer id);
    ResponseEntity<PanicExpandedDTO> addPanic(Integer id, ReasonDTO panic);
    ResponseEntity<RideDTO> acceptRide(Integer id);
    ResponseEntity<RideDTO> endRide(Integer id);
    ResponseEntity<RideDTO> cancelRideWithExplanation(Integer id, ReasonDTO reason);

    boolean isBookableRide(Ride newRideRequest);

    void save(Ride newRideRequest);
}
