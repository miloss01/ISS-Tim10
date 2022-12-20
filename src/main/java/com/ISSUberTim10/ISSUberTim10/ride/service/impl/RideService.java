package com.ISSUberTim10.ISSUberTim10.ride.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RideRepository;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RideService implements IRideService {
    @Autowired
    RideRepository rideRepository;

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
    public ResponseEntity<RideDTO> cancelRide(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<PanicExpandedDTO> addPanic(Integer id, ReasonDTO panic) {
        return null;
    }

    @Override
    public ResponseEntity<RideDTO> acceptRide(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<RideDTO> endRide(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<RideDTO> cancelRideWithExplanation(Integer id, ReasonDTO reason) {
        return null;
    }

}
