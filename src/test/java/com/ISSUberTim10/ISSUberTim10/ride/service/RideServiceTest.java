package com.ISSUberTim10.ISSUberTim10.ride.service;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.Route;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RideRepository;
import com.ISSUberTim10.ISSUberTim10.ride.service.impl.RideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        when(rideRepository.findAll()).thenReturn(Arrays.asList(pendingRide, acceptedRide));

        List<Ride> rides = (List<Ride>) rideService.getAll();
        assertThat(rides.size()).isEqualTo(2);
        assertThat(rides.get(0).getId()).isEqualTo(1L);
        assertThat(rides.get(1).getId()).isEqualTo(2L);

        verify(rideRepository, times(1)).findAll();
    }

    @Test
    public void shouldNotFindAnyRides() {
        when(rideRepository.findAll()).thenReturn(Arrays.asList());

        List<Ride> rides = (List<Ride>) rideService.getAll();
        assertThat(rides.size()).isEqualTo(0);

        verify(rideRepository, times(1)).findAll();
    }

}
