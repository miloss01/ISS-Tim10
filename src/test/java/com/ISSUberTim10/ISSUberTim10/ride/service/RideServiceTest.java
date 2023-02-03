package com.ISSUberTim10.ISSUberTim10.ride.service;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.Route;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RideRepository;
import com.ISSUberTim10.ISSUberTim10.ride.service.impl.RideService;
import org.glassfish.jersey.internal.inject.Custom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RideServiceTest {

    @Mock
    private RideRepository rideRepository;
    @Autowired
    @InjectMocks
    private RideService rideService;

    private Ride pendingRide = null;
    private Ride acceptedRide = null;
    private Ride activeRide = null;
    private Ride finishedRide = null;

    private Ride getDummyRide() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);
        Route route = new Route();
        route.setId(1L);
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(route);
        Ride ride = new Ride(null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), 100.0, new Driver(), passengers, routes, 10, null, false, true, true, null);

        return ride;
    }

    @BeforeEach
    public void setup() {
        pendingRide = getDummyRide();
        pendingRide.setId(1L);
        pendingRide.setRideStatus(Ride.RIDE_STATUS.pending);

        acceptedRide = getDummyRide();
        acceptedRide.setId(2L);
        acceptedRide.setRideStatus(Ride.RIDE_STATUS.accepted);

        activeRide = getDummyRide();
        activeRide.setId(3L);
        activeRide.setRideStatus(Ride.RIDE_STATUS.active);

        finishedRide = getDummyRide();
        finishedRide.setId(4L);
        finishedRide.setRideStatus(Ride.RIDE_STATUS.finished);
    }

    @Test
    public void shouldFindAllRides() {
        List<Ride> mocks = Arrays.asList(pendingRide, acceptedRide);
        when(rideRepository.findAll()).thenReturn(mocks);

        List<Ride> rides = (List<Ride>) rideService.getAll();
        assertThat(rides.size()).isEqualTo(mocks.size());
        assertThat(rides.get(0).getId()).isEqualTo(mocks.get(0).getId());
        assertThat(rides.get(1).getId()).isEqualTo(mocks.get(1).getId());

        verify(rideRepository, times(1)).findAll();
    }

    @Test
    public void shouldNotFindAnyRides() {
        when(rideRepository.findAll()).thenReturn(Arrays.asList());

        List<Ride> rides = (List<Ride>) rideService.getAll();
        assertThat(rides.size()).isEqualTo(0);

        verify(rideRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindAllRidesWithoutPageable() {
        List<Ride> mocks = Arrays.asList(pendingRide, acceptedRide);
        Page page = new PageImpl(mocks);
        when(rideRepository.findAll(Pageable.unpaged())).thenReturn(page);

        List<Ride> rides = rideService.getAll(Pageable.unpaged());
        assertThat(rides.size()).isEqualTo(mocks.size());
        assertThat(rides.get(0).getId()).isEqualTo(mocks.get(0).getId());
        assertThat(rides.get(1).getId()).isEqualTo(mocks.get(1).getId());

        verify(rideRepository, times(1)).findAll(Pageable.unpaged());
    }

    @Test
    public void shouldFindAllRidesWithPageable() {
        List<Ride> mocks = Arrays.asList(finishedRide, activeRide);
        Page page = new PageImpl(mocks);
        Pageable pageable = PageRequest.of(1, 2, Sort.by("id").descending());
        when(rideRepository.findAll(pageable)).thenReturn(page);

        List<Ride> rides = rideService.getAll(pageable);
        assertThat(rides.size()).isEqualTo(2);
        assertThat(rides.get(0).getId()).isEqualTo(finishedRide.getId());
        assertThat(rides.get(1).getId()).isEqualTo(activeRide.getId());

        verify(rideRepository, times(1)).findAll(pageable);
    }

    @Test
    public void shouldFindRideWithProvidedId() {
        when(rideRepository.findById(anyLong())).thenReturn(Optional.of(pendingRide));

        Ride ride = rideService.getRideById(anyLong());

        assertThat(ride).isEqualTo(pendingRide);

        verify(rideRepository, times(1)).findById(anyLong());
    }

    @Test
    public void shouldThrowExceptionForNoRideFound() {
        when(rideRepository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> {
            Ride ride = rideService.getRideById(anyLong());
        });

        assertThat(thrown).isInstanceOf(CustomException.class);
        CustomException customException = (CustomException) thrown;
        assertThat(customException.message).isEqualTo("Ride does not exist!");
        assertThat(customException.httpStatus).isEqualTo(HttpStatus.NOT_FOUND);

        verify(rideRepository, times(1)).findById(anyLong());

    }

    @Test
    public void shouldFindAllRidesWithProvidedDriverWithoutPageable() {
        Driver driver = new Driver();
        driver.setId(1L);
        pendingRide.setDriver(driver);
        acceptedRide.setDriver(driver);
        List<Ride> mocks = Arrays.asList(pendingRide, acceptedRide);
        Page page = new PageImpl(mocks);
        when(rideRepository.findAllByDriver(Pageable.unpaged(), driver)).thenReturn(page);

        List<Ride> rides = rideService.getByDriver(Pageable.unpaged(), driver);
        for (Ride ride : rides)
            assertThat(ride.getDriver()).isEqualTo(driver);

        verify(rideRepository, times(1)).findAllByDriver(Pageable.unpaged(), driver);

    }

    @Test
    public void shouldNotFindAnyRidesWithProvidedDriverWithoutPageable() {
        Driver driver = new Driver();
        driver.setId(10L);
        List<Ride> mocks = Arrays.asList(pendingRide, acceptedRide);
        Page page = new PageImpl(mocks);
        when(rideRepository.findAllByDriver(Pageable.unpaged(), driver)).thenReturn(page);

        List<Ride> rides = rideService.getByDriver(Pageable.unpaged(), driver);
        for (Ride ride : rides)
            assertThat(ride.getDriver()).isNotEqualTo(driver);

        verify(rideRepository, times(1)).findAllByDriver(Pageable.unpaged(), driver);

    }

    @Test
    public void shouldFindAllRidesWithProvidedDriverAndPageable() {
        Driver driver = new Driver();
        driver.setId(1L);
        pendingRide.setDriver(driver);
        acceptedRide.setDriver(driver);
        List<Ride> mocks = Arrays.asList(pendingRide, acceptedRide);
        Page page = new PageImpl(mocks);
        Pageable pageable = PageRequest.of(1, 1, Sort.by("id"));
        when(rideRepository.findAllByDriver(pageable, driver)).thenReturn(page);

        List<Ride> rides = rideService.getByDriver(pageable, driver);
        for (Ride ride : rides)
            assertThat(ride.getDriver()).isEqualTo(driver);

        verify(rideRepository, times(1)).findAllByDriver(pageable, driver);

    }

    @Test
    public void shouldNotFindAnyRidesWithProvidedDriverAndPageable() {
        Driver driver = new Driver();
        driver.setId(10L);
        List<Ride> mocks = Arrays.asList(pendingRide, acceptedRide);
        Page page = new PageImpl(mocks);
        Pageable pageable = PageRequest.of(1, 1, Sort.by("id"));
        when(rideRepository.findAllByDriver(pageable, driver)).thenReturn(page);

        List<Ride> rides = rideService.getByDriver(pageable, driver);
        for (Ride ride : rides)
            assertThat(ride.getDriver()).isNotEqualTo(driver);

        verify(rideRepository, times(1)).findAllByDriver(pageable, driver);

    }

    @Test
    public void shouldFindAllRidesWithProvidedPassengerWithoutPageable() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        pendingRide.setPassengers(Arrays.asList(passenger));
        acceptedRide.setPassengers(Arrays.asList(passenger));
        List<Ride> mocks = Arrays.asList(pendingRide, acceptedRide);
        Page page = new PageImpl(mocks);
        when(rideRepository.findAllByPassengersContaining(Pageable.unpaged(), passenger)).thenReturn(page);

        List<Ride> rides = rideService.getByPassenger(Pageable.unpaged(), passenger);
        for (Ride ride : rides)
            assertThat(ride.getPassengers()).contains(passenger);

        verify(rideRepository, times(1)).findAllByPassengersContaining(Pageable.unpaged(), passenger);

    }

    @Test
    public void shouldNotFindAnyRidesWithProvidedPassengerWithoutPageable() {
        Passenger passenger = new Passenger();
        passenger.setId(10L);
        List<Ride> mocks = Arrays.asList(pendingRide, acceptedRide);
        Page page = new PageImpl(mocks);
        when(rideRepository.findAllByPassengersContaining(Pageable.unpaged(), passenger)).thenReturn(page);

        List<Ride> rides = rideService.getByPassenger(Pageable.unpaged(), passenger);
        for (Ride ride : rides)
            assertThat(ride.getPassengers()).doesNotContain(passenger);

        verify(rideRepository, times(1)).findAllByPassengersContaining(Pageable.unpaged(), passenger);

    }

    @Test
    public void shouldFindAllRidesWithProvidedPassengerAndPageable() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        pendingRide.setPassengers(Arrays.asList(passenger));
        acceptedRide.setPassengers(Arrays.asList(passenger));
        List<Ride> mocks = Arrays.asList(pendingRide, acceptedRide);
        Page page = new PageImpl(mocks);
        Pageable pageable = PageRequest.of(1, 1, Sort.by("id"));
        when(rideRepository.findAllByPassengersContaining(pageable, passenger)).thenReturn(page);

        List<Ride> rides = rideService.getByPassenger(pageable, passenger);
        for (Ride ride : rides)
            assertThat(ride.getPassengers()).contains(passenger);

        verify(rideRepository, times(1)).findAllByPassengersContaining(pageable, passenger);

    }

    @Test
    public void shouldNotFindAnyRidesWithProvidedPassengerAndPageable() {
        Passenger passenger = new Passenger();
        passenger.setId(10L);
        List<Ride> mocks = Arrays.asList(pendingRide, acceptedRide);
        Page page = new PageImpl(mocks);
        Pageable pageable = PageRequest.of(1, 1, Sort.by("id"));
        when(rideRepository.findAllByPassengersContaining(pageable, passenger)).thenReturn(page);

        List<Ride> rides = rideService.getByPassenger(pageable, passenger);
        for (Ride ride : rides)
            assertThat(ride.getPassengers()).doesNotContain(passenger);

        verify(rideRepository, times(1)).findAllByPassengersContaining(pageable, passenger);

    }

    @Test
    public void shouldFindRideWithProvidedDriverAndStatus() {
        Driver driver = new Driver();
        driver.setId(2L);
        when(rideRepository.findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.pending)).thenReturn(Optional.of(Arrays.asList(pendingRide)));
        when(rideRepository.findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.accepted)).thenReturn(Optional.of(Arrays.asList(acceptedRide)));
        when(rideRepository.findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.active)).thenReturn(Optional.of(Arrays.asList(activeRide)));
        when(rideRepository.findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.finished)).thenReturn(Optional.of(Arrays.asList(finishedRide)));

        Ride ride = rideService.getByDriverAndStatus(driver, Ride.RIDE_STATUS.pending);
        assertThat(ride).isEqualTo(pendingRide);
        ride = rideService.getByDriverAndStatus(driver, Ride.RIDE_STATUS.accepted);
        assertThat(ride).isEqualTo(acceptedRide);
        ride = rideService.getByDriverAndStatus(driver, Ride.RIDE_STATUS.active);
        assertThat(ride).isEqualTo(activeRide);
        ride = rideService.getByDriverAndStatus(driver, Ride.RIDE_STATUS.finished);
        assertThat(ride).isEqualTo(finishedRide);

        verify(rideRepository, times(1)).findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.pending);
        verify(rideRepository, times(1)).findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.accepted);
        verify(rideRepository, times(1)).findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.active);
        verify(rideRepository, times(1)).findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.finished);
    }

    @Test
    public void shouldNotFindRideWithProvidedDriverAndStatus() {
        Driver driver = new Driver();
        driver.setId(2L);
        when(rideRepository.findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.pending)).thenReturn(Optional.of(new ArrayList<Ride>()));

        Throwable thrown = catchThrowable(() -> {
            Ride ride = rideService.getByDriverAndStatus(driver, Ride.RIDE_STATUS.pending);
        });

        assertThat(thrown).isInstanceOf(CustomException.class);
        CustomException customException = (CustomException) thrown;
        assertThat(customException.message).isEqualTo(Ride.RIDE_STATUS.pending.toString() + " ride does not exist!");
        assertThat(customException.httpStatus).isEqualTo(HttpStatus.NOT_FOUND);

        verify(rideRepository, times(1)).findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.pending);
    }

    @Test
    public void shouldFindRideWithProvidedPassengerAndStatus() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        when(rideRepository.findByPassengersContainingAndRideStatus(passenger, Ride.RIDE_STATUS.pending)).thenReturn(Optional.of(pendingRide));
        when(rideRepository.findByPassengersContainingAndRideStatus(passenger, Ride.RIDE_STATUS.accepted)).thenReturn(Optional.of(acceptedRide));
        when(rideRepository.findByPassengersContainingAndRideStatus(passenger, Ride.RIDE_STATUS.active)).thenReturn(Optional.of(activeRide));
        when(rideRepository.findByPassengersContainingAndRideStatus(passenger, Ride.RIDE_STATUS.finished)).thenReturn(Optional.of(finishedRide));

        Ride ride = rideService.getByPassengerAndStatus(passenger, Ride.RIDE_STATUS.pending);
        assertThat(ride).isEqualTo(pendingRide);
        ride = rideService.getByPassengerAndStatus(passenger, Ride.RIDE_STATUS.accepted);
        assertThat(ride).isEqualTo(acceptedRide);
        ride = rideService.getByPassengerAndStatus(passenger, Ride.RIDE_STATUS.active);
        assertThat(ride).isEqualTo(activeRide);
        ride = rideService.getByPassengerAndStatus(passenger, Ride.RIDE_STATUS.finished);
        assertThat(ride).isEqualTo(finishedRide);

        verify(rideRepository, times(1)).findByPassengersContainingAndRideStatus(passenger, Ride.RIDE_STATUS.pending);
        verify(rideRepository, times(1)).findByPassengersContainingAndRideStatus(passenger, Ride.RIDE_STATUS.accepted);
        verify(rideRepository, times(1)).findByPassengersContainingAndRideStatus(passenger, Ride.RIDE_STATUS.active);
        verify(rideRepository, times(1)).findByPassengersContainingAndRideStatus(passenger, Ride.RIDE_STATUS.finished);
    }

    @Test
    public void shouldNotFindRideWithProvidedPassengerAndStatus() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        when(rideRepository.findByPassengersContainingAndRideStatus(passenger, Ride.RIDE_STATUS.pending)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> {
            Ride ride = rideService.getByPassengerAndStatus(passenger, Ride.RIDE_STATUS.pending);
        });

        assertThat(thrown).isInstanceOf(CustomException.class);
        CustomException customException = (CustomException) thrown;
        assertThat(customException.message).isEqualTo(Ride.RIDE_STATUS.pending.toString() + " ride does not exist!");
        assertThat(customException.httpStatus).isEqualTo(HttpStatus.NOT_FOUND);

        verify(rideRepository, times(1)).findByPassengersContainingAndRideStatus(passenger, Ride.RIDE_STATUS.pending);
    }

    @Test
    public void shouldFindActiveRideForProvidedDriver() {
        Driver driver = new Driver();
        driver.setId(2L);
        when(rideRepository.findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.active)).thenReturn(Optional.of(Arrays.asList(activeRide)));

        Ride ride = rideService.getActiveDriverRide(driver);
        assertThat(ride).isEqualTo(activeRide);

        verify(rideRepository, times(1)).findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.active);
    }

    @Test
    public void shouldNotFindActiveRideForProvidedDriver() {
        Driver driver = new Driver();
        driver.setId(2L);
        when(rideRepository.findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.active)).thenReturn(Optional.of(new ArrayList<Ride>()));

        Throwable thrown = catchThrowable(() -> {
            Ride ride = rideService.getActiveDriverRide(driver);
        });

        assertThat(thrown).isInstanceOf(CustomException.class);
        CustomException customException = (CustomException) thrown;
        assertThat(customException.message).isEqualTo("Active ride does not exist!");
        assertThat(customException.httpStatus).isEqualTo(HttpStatus.NOT_FOUND);

        verify(rideRepository, times(1)).findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.active);
    }
}
