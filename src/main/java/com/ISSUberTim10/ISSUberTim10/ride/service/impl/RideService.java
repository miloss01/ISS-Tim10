package com.ISSUberTim10.ISSUberTim10.ride.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.ride.Rejection;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RejectionRepository;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RideRepository;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RideService implements IRideService {
    @Autowired
    RideRepository rideRepository;

    @Autowired
    RejectionRepository rejectionRepository;

    @Override
    public Collection<Ride> getAll() {
        return rideRepository.findAll();
    }

    @Override
    public void createAll() {

    }

    @Override
    public void deleteAll() {
        rideRepository.deleteAll();
    }

    @Override
    public Page<Ride> getByUser(Long id, Pageable page) {
        return rideRepository.findAll((org.springframework.data.domain.Pageable) page);
    }

    @Override
    public ResponseEntity<RideDTO> addRide(RideCreationDTO rideCreation) {
        return null;
    }

    @Override
    public ResponseEntity<RideDTO> getRideByDriverId(Integer driverId) {
        return null;
    }

    @Override
    public ResponseEntity<RideDTO> getRideById(Integer id) {
        Ride ride = rideRepository.getById(Long.valueOf(id));
        RideDTO rideDTO = new RideDTO(ride);
        return new ResponseEntity<>(rideDTO, HttpStatus.OK);

    }

    @Override
    public Ride getRideById(Long id) {

        Optional<Ride> ride = rideRepository.findById(id);

        if (!ride.isPresent())
            throw new CustomException("Ride does not exist!", HttpStatus.NOT_FOUND);

        return ride.get();

    }

    @Override
    public List<Ride> getByDriver(Pageable pageable, Driver driver) {

        return rideRepository.findAllByDriver(pageable, driver).getContent();

    }

    @Override
    public List<Ride> getByPassenger(Pageable pageable, Passenger passenger) {
        return rideRepository.findAllByPassengersContaining(pageable, passenger).getContent();
    }

    @Override
    public ResponseEntity<RideDTO> cancelRide(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<PanicExpandedDTO> addPanic(Integer id, ReasonDTO panic) {
        return null;
    }

    @Override
    public ResponseEntity<RideDTO> acceptRide(Integer id) {

        Ride ride = rideRepository.getById(Long.valueOf(id));
        ride.setRideStatus(Ride.RIDE_STATUS.accepted);
        rideRepository.save(ride);
        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<RideDTO> endRide(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<RideDTO> cancelRideWithExplanation(Integer id, ReasonDTO reason) {

        Optional<Ride> found = rideRepository.findById(Long.valueOf(id));
        if (!found.isPresent())
            throw new CustomException("Passenger does not exist!", HttpStatus.NOT_FOUND);
        Ride ride = found.get();
        ride.setRideStatus(Ride.RIDE_STATUS.rejected);
        Rejection rejection;
        try {
            rejection = rejectionRepository.getById(ride.getRejection().getId());
            rejection = new Rejection(rejection.getId(), ride, reason.getReason(), ride.getDriver(), LocalDateTime.now()); //TODO kako ovo

        }catch (Exception exception) {
            rejection = new Rejection(0L, ride, reason.getReason(), ride.getDriver(), LocalDateTime.now()); //TODO kako ovo
        }
        rejectionRepository.save(rejection);
        ride.setRejection(rejection); //TODO kako ovo
        rideRepository.save(ride);

        rejection = rejectionRepository.getById(ride.getRejection().getId());
        System.out.println(rejection.getId());
        System.out.println("-----------------");

        return new ResponseEntity<>(new RideDTO(ride), HttpStatus.OK);
    }

    @Override
    public boolean isBookableRide(Ride newRideRequest) {
        ArrayList<Ride> rides = rideRepository.findAllByStartTimeGreaterThanEqualOrEndTimeLessThanEqual(LocalDateTime.now().minusHours(5), LocalDateTime.now().plusHours(5));
        System.out.println(rides.size());
        System.out.println(rides.size());
        return true;
    }

    @Override
    public void save(Ride newRideRequest) {
        rideRepository.save(newRideRequest);
    }

}
