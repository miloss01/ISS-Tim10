package com.ISSUberTim10.ISSUberTim10.ride.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.ride.Report;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.StatisticsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface IRideService {
    public Collection<Ride> getAll();//done
    public List<Ride> getAll(Pageable pageable);//done

    public void createAll();

    public void deleteAll();

    public Page<Ride> getByUser(Long id, Pageable page);//mozda bi mogla da se obrise metoda, jer se u kontroleru i ne koristi
    public Ride getRideById(Long id);//done
    public List<Ride> getByDriver(Pageable pageable, Driver driver);//done
    public List<Ride> getByPassenger(Pageable pageable, Passenger passenger);//done

//    public ArrayList<Ride> getByDriverAndStatus(Driver driver, ArrayList<Ride.RIDE_STATUS> statuses);
//    public Ride getByPassengerAndStatus(Passenger passenger, ArrayList<Ride.RIDE_STATUS> statuses);
//    ResponseEntity<RideDTO> cancelRide(Integer id);
//    ResponseEntity<PanicExpandedDTO> addPanic(Integer id, ReasonDTO panic);

    public Ride getByDriverAndStatus(Driver driver, Ride.RIDE_STATUS status);//done
    public Ride getActiveDriverRide(Driver driver);//done
    public Ride getByPassengerAndStatus(Passenger passenger, Ride.RIDE_STATUS status);//done

    Ride acceptRide(Ride id);
    Ride endRide(Ride id);
    Ride cancelRideWithExplanation(Ride id, String reason);

    boolean isBookableRide(Ride newRideRequest);

    Ride save(Ride newRideRequest);

    Ride withdrawRide(Ride ride);

    Ride startRide(Ride ride);

    Ride getDriverEarliestAcceptedRide(Driver driver);
}
