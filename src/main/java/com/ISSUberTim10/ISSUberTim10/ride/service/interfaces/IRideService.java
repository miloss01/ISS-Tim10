package com.ISSUberTim10.ISSUberTim10.ride.service.interfaces;

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

public interface IRideService {
    public Collection<Ride> getAll();

    public void createAll();

    public void deleteAll();

    public Page<Ride> getByUser(Long id, Pageable page);
    ResponseEntity<RideDTO> addRide(RideCreationDTO rideCreation);
    ResponseEntity<RideDTO> getRideByDriverId(Integer driverId);
    ResponseEntity<RideDTO> getRideById(Integer id);
    ResponseEntity<RideDTO> cancelRide(Integer id);
    ResponseEntity<PanicExpandedDTO> addPanic(Integer id, ReasonDTO panic);
    ResponseEntity<RideDTO> acceptRide(Integer id);
    ResponseEntity<RideDTO> endRide(Integer id);
    ResponseEntity<RideDTO> cancelRideWithExplanation(Integer id, ReasonDTO reason);

    }
