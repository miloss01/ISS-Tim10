package com.ISSUberTim10.ISSUberTim10.ride.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.PassengerRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.VehicleType;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.WorkingTime;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.VehicleRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.VehicleTypeRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.WorkingTimeRepository;
import com.ISSUberTim10.ISSUberTim10.auth.JwtTokenUtil;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.ride.*;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.repository.CoordinatesRepository;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RejectionRepository;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RideRepository;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RouteRepository;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class RideService implements IRideService {
    @Autowired
    RideRepository rideRepository;

    @Autowired
    RejectionRepository rejectionRepository;

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    CoordinatesRepository coordinatesRepository;
    @Autowired
    WorkingTimeRepository workingTimeRepository;

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
    public Ride getByDriverAndStatus(Driver driver, Ride.RIDE_STATUS status) {

        Optional<Ride> ride = rideRepository.findByDriverAndRideStatus(driver, status);

        if (!ride.isPresent())
            throw new CustomException(status.toString() + " ride does not exist!", HttpStatus.NOT_FOUND);

        return ride.get();

    }

    @Override
    public Ride getByPassengerAndStatus(Passenger passenger, Ride.RIDE_STATUS status) {

        Optional<Ride> ride = rideRepository.findByPassengersContainingAndRideStatus(passenger, status);

        if (!ride.isPresent())
            throw new CustomException(status.toString() + " ride does not exist!", HttpStatus.NOT_FOUND);

        return ride.get();

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
    public Ride acceptRide(Ride ride) {
        if (ride.getRideStatus() != Ride.RIDE_STATUS.pending) {
            throw new CustomException("Cannot accept a ride that is not in status PENDING!", HttpStatus.BAD_REQUEST);
        }
        ride.setRideStatus(Ride.RIDE_STATUS.accepted);
        return rideRepository.save(ride);
    }

    @Override
    public Ride endRide(Ride ride) {

        if (ride.getRideStatus() != Ride.RIDE_STATUS.active) {
            throw new CustomException("Cannot end a ride that is not in status FINISHED!", HttpStatus.BAD_REQUEST);
        }
        ride.setRideStatus(Ride.RIDE_STATUS.finished);
        return rideRepository.save(ride);
    }

    @Override
    public Ride cancelRideWithExplanation(Ride ride, String reason) {
        if (ride.getRideStatus() != Ride.RIDE_STATUS.pending) {
            throw new CustomException("Cannot cancel a ride that is not in status PENDING!", HttpStatus.BAD_REQUEST);
        }
        ride.setRideStatus(Ride.RIDE_STATUS.rejected);
        Rejection rejection;
        Optional<Rejection> found = rejectionRepository.findById(ride.getRejection().getId());
        if (found.isPresent()) {
            rejection = found.get();
            rejection.setReason(reason);
            rejection.setRejectionTime(LocalDateTime.now());
        }else {
            rejection = new Rejection(0L, ride, reason, ride.getDriver(), LocalDateTime.now());
        }
        rejectionRepository.save(rejection);
        ride.setRejection(rejection);
        return rideRepository.save(ride);
    }

    @Override
    public boolean isBookableRide(Ride newRideRequest) {
        ArrayList<Ride.RIDE_STATUS> statuses = new ArrayList<>();
        statuses.add(Ride.RIDE_STATUS.active);
        statuses.add(Ride.RIDE_STATUS.accepted);
        statuses.add(Ride.RIDE_STATUS.pending);
        if (isPassengerAlreadyInARide(newRideRequest, statuses)) return false;

        ArrayList<Vehicle> vehicles = findAppropriateVehicles(newRideRequest);
        if(vehicles.size()==0) return false;
        Driver availableDriver = findBestDriver(vehicles, statuses, newRideRequest);
        if (availableDriver.getId() == -1L) return false;
        fillRideRequest(newRideRequest, availableDriver);
        return true;
    }

    private ArrayList<Vehicle> findAppropriateVehicles(Ride newRideRequest){
        ArrayList<Vehicle> vehicles = findAllVehiclesByType(newRideRequest.getVehicleType());
        vehicles.removeIf(vehicle -> newRideRequest.isBabyFlag() && !vehicle.isBabyFlag());
        vehicles.removeIf(vehicle -> newRideRequest.isPetsFlag() && !vehicle.isPetsFlag());
        return vehicles;
    }

    private void fillRideRequest(Ride newRideRequest, Driver availableDriver) {
        int distance = 10;
        Rejection rejection = new Rejection();
        rejection.setId(0L);
        rejection.setRejectionTime(LocalDateTime.now());
        ArrayList<Passenger> passengers = new ArrayList<>();
        for (Passenger passenger: newRideRequest.getPassengers()){
            Optional<Passenger> found = passengerRepository.findByEmail(passenger.getEmail());
            found.ifPresent(passengers::add);
        }
        ArrayList<Route> routes = (ArrayList<Route>) newRideRequest.getRoutes();
        routes.get(0).setMileage(distance);
        routes.get(0).setOrderr(1);
        routes.get(0).getDepartureCoordinates().setId(0L);
        routes.get(0).getDestinationCoordinates().setId(0L);
        VehicleType vehicleType = vehicleTypeRepository.getByName(Vehicle.VEHICLE_TYPE.valueOf(newRideRequest.getVehicleType()));
        newRideRequest.setId(0L);
        newRideRequest.setPrice(vehicleType.getPrice() + distance * 120);
        newRideRequest.setDriver(availableDriver);
        newRideRequest.setPassengers(passengers);
        newRideRequest.setRoutes(routes);
        newRideRequest.setRideStatus(Ride.RIDE_STATUS.pending);
        newRideRequest.setRejection(rejection);
        rejection.setRide(newRideRequest);
    }

    private Driver findBestDriver(ArrayList<Vehicle> vehicles, ArrayList<Ride.RIDE_STATUS> statuses, Ride newRideRequest) {
        Driver closestDriver = new Driver();
        closestDriver.setId(-1L);
        long closestTime = 1000000000;
        for (Vehicle vehicle : vehicles) {
            ArrayList<Ride> rides = rideRepository.findAllByRideStatusInAndDriver(statuses, vehicle.getDriver());
            if (vehicle.getDriver().isActiveFlag() && isDriverCapable(vehicle.getDriver())) {
                if (canBookThen(rides, newRideRequest)) return vehicle.getDriver();
                for (Ride ride : rides) {
                    long minutesBetween = Math.abs(ChronoUnit.MINUTES.between(ride.getEndTime(), newRideRequest.getStartTime()));
                    if (minutesBetween < closestTime) {
                        closestTime = minutesBetween;
                        closestDriver = vehicle.getDriver();
                    }
                }
            }
        }
        if (closestDriver.getId() != -1L) {
            newRideRequest.setStartTime(newRideRequest.getStartTime().plusMinutes(closestTime));
        }
        return closestDriver;
    }

    private boolean canBookThen(ArrayList<Ride> rides, Ride newRideRequest) {
        for (Ride ride: rides) {
            if((ride.getStartTime().isBefore(newRideRequest.getStartTime()) && newRideRequest.getStartTime().isBefore(ride.getEndTime()))
            && (ride.getStartTime().isBefore(newRideRequest.getEndTime()) && newRideRequest.getEndTime().isBefore(ride.getEndTime()))
            && (ride.getStartTime().isBefore(newRideRequest.getStartTime()) && ride.getEndTime().isAfter(newRideRequest.getEndTime())))
                return false;
        }
        return true;
    }

    private boolean isDriverCapable(Driver driver) {
        long totalMinutes = 0;
        ArrayList<WorkingTime> workingTimes = workingTimeRepository.findByDriverAndStartTimeGreaterThanEqual(driver, LocalDateTime.now().minusDays(1));
        for (WorkingTime workingTime:workingTimes) {
            if (workingTime.getStartTime().isEqual(workingTime.getEndTime())){
                totalMinutes += ChronoUnit.MINUTES.between(workingTime.getStartTime(), LocalDateTime.now());
            }
            totalMinutes += ChronoUnit.MINUTES.between(workingTime.getStartTime(), workingTime.getEndTime());
        }
//        System.out.println("totalMinutes--------------");
//        System.out.println(totalMinutes);
        return (totalMinutes <= 8*60);
    }

    private ArrayList<Vehicle> findAllVehiclesByType(String vehicleType) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        Optional<VehicleType> found = Optional.ofNullable(vehicleTypeRepository.getByName(Vehicle.VEHICLE_TYPE.valueOf(vehicleType)));
        if (!found.isPresent()) return vehicles;
        vehicles = vehicleRepository.findByVehicleType(found.get());
        return vehicles;
    }

    private boolean isPassengerAlreadyInARide(Ride newRideRequest, ArrayList<Ride.RIDE_STATUS> statuses) {
        Passenger passenger = new Passenger();
        for (Passenger passengerIncluded: newRideRequest.getPassengers()){
            if (passengerIncluded.getId() != 0L) passenger.setId(passengerIncluded.getId());
        }
        Optional<Ride> found = rideRepository.findByPassengersContainingAndRideStatusIn(passenger, statuses);
        return found.isPresent();
    }

    @Override
    public void save(Ride newRideRequest) {
        ArrayList<Route> routes = (ArrayList<Route>) newRideRequest.getRoutes();
        Coordinates departure= coordinatesRepository.save(routes.get(0).getDepartureCoordinates());
        Coordinates destination= coordinatesRepository.save(routes.get(0).getDestinationCoordinates());
        routes.get(0).setDepartureCoordinates(departure);
        routes.get(0).setDestinationCoordinates(destination);
        Route route = routeRepository.save(routes.get(0));
        routes = new ArrayList<>();
        routes.add(route);
        newRideRequest.setRoutes(routes);
        rideRepository.save(newRideRequest);
    }

    @Override
    public Ride withdrawRide(Ride ride) {
        if (!(ride.getRideStatus() == Ride.RIDE_STATUS.pending || ride.getRideStatus() == Ride.RIDE_STATUS.active)) {
            throw new CustomException("Cannot cancel a ride that is not in status PENDING or STARTED!", HttpStatus.BAD_REQUEST);
        }
        ride.setRideStatus(Ride.RIDE_STATUS.rejected);
        return rideRepository.save(ride);
    }

    @Override
    public Ride startRide(Ride ride) {
        if (ride.getRideStatus() != Ride.RIDE_STATUS.accepted) {
            throw new CustomException("Cannot start a ride that is not in status ACCEPTED!", HttpStatus.BAD_REQUEST);
        }
        ride.setRideStatus(Ride.RIDE_STATUS.active);
        return rideRepository.save(ride);
    }

}
