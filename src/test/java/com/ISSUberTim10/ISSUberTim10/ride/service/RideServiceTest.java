package com.ISSUberTim10.ISSUberTim10.ride.service;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.PassengerRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.VehicleType;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.WorkingTime;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.VehicleRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.VehicleTypeRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.WorkingTimeRepository;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomExceptionWithMessage;
import com.ISSUberTim10.ISSUberTim10.ride.Coordinates;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.Route;
import com.ISSUberTim10.ISSUberTim10.ride.repository.CoordinatesRepository;
import com.ISSUberTim10.ISSUberTim10.ride.Rejection;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RejectionRepository;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RideRepository;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RouteRepository;
import com.ISSUberTim10.ISSUberTim10.ride.service.impl.RideService;
import com.beust.ah.A;
import org.glassfish.jersey.internal.inject.Custom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RideServiceTest {

    @Mock
    private RideRepository rideRepository;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private CoordinatesRepository coordinatesRepository;
    @Mock
    private RejectionRepository rejectionRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private WorkingTimeRepository workingTimeRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleTypeRepository vehicleTypeRepository;
    @Autowired
    @InjectMocks
    private RideService rideService;

    private Ride pendingRide = null;
    private Ride acceptedRide = null;
    private Ride activeRide = null;
    private Ride finishedRide = null;
    private Ride rejectedRide = null;
    private final String REJECTION_REASON = "I have to go";
    private ArrayList<Ride.RIDE_STATUS> statuses = new ArrayList<>();


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

        rejectedRide = getDummyRide();
        rejectedRide.setId(4L);
        rejectedRide.setRideStatus(Ride.RIDE_STATUS.rejected);

        statuses.add(Ride.RIDE_STATUS.active);
        statuses.add(Ride.RIDE_STATUS.accepted);
        statuses.add(Ride.RIDE_STATUS.pending);
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

        List<Ride> rides = rideService.getByPassenger(Pageable.unpaged(), passenger);
        for (Ride ride : rides)
            assertThat(ride.getPassengers()).contains(passenger);

        verify(rideRepository, times(1)).findByPassengersId(Pageable.unpaged(), passenger.getId());

    }

    @Test
    public void shouldNotFindAnyRidesWithProvidedPassengerWithoutPageable() {
        Passenger passenger = new Passenger();
        passenger.setId(10L);
        List<Ride> mocks = Arrays.asList(pendingRide, acceptedRide);
        Page page = new PageImpl(mocks);

        List<Ride> rides = rideService.getByPassenger(Pageable.unpaged(), passenger);
        for (Ride ride : rides)
            assertThat(ride.getPassengers()).doesNotContain(passenger);

        verify(rideRepository, times(1)).findByPassengersId(Pageable.unpaged(), passenger.getId());

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

        List<Ride> rides = rideService.getByPassenger(pageable, passenger);
        for (Ride ride : rides)
            assertThat(ride.getPassengers()).contains(passenger);

        verify(rideRepository, times(1)).findByPassengersId(pageable, passenger.getId());

    }

    @Test
    public void shouldNotFindAnyRidesWithProvidedPassengerAndPageable() {
        Passenger passenger = new Passenger();
        passenger.setId(10L);
        List<Ride> mocks = Arrays.asList(pendingRide, acceptedRide);
        Page page = new PageImpl(mocks);
        Pageable pageable = PageRequest.of(1, 1, Sort.by("id"));

        List<Ride> rides = rideService.getByPassenger(pageable, passenger);
        for (Ride ride : rides)
            assertThat(ride.getPassengers()).doesNotContain(passenger);

        verify(rideRepository, times(1)).findByPassengersId(pageable, passenger.getId());

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

    @Test
    public void shouldNotAcceptRideNotInPendingStatus() {
        Throwable thrown = catchThrowable(() -> {
            rideService.acceptRide(activeRide);
        });

        assertThat(thrown).isInstanceOf(CustomExceptionWithMessage.class);
        CustomExceptionWithMessage customException = (CustomExceptionWithMessage) thrown;
        assertThat(customException.message).isEqualTo("Cannot accept a ride that is not in status PENDING!");
        assertThat(customException.httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void shouldAcceptRideInPendingStatus() {
        when(rideRepository.save(pendingRide)).thenReturn(pendingRide);

        Ride ride = rideService.acceptRide(pendingRide);

        assertThat(ride.getRideStatus().toString()).isEqualTo(Ride.RIDE_STATUS.accepted.toString());

    }

    @Test
    public void shouldSaveRideRequest() {
        Ride newRideRequest = getDummyRide();

        Coordinates departureCoordinates = new Coordinates();
        departureCoordinates.setId(1L);
        departureCoordinates.setLatitude(14);
        departureCoordinates.setLongitude(15);
        departureCoordinates.setAddress("Some address");

        Coordinates destinationCoordinates = new Coordinates();
        destinationCoordinates.setId(1L);
        destinationCoordinates.setLatitude(14);
        destinationCoordinates.setLongitude(15);
        destinationCoordinates.setAddress("Some address");

        Route route = new Route();
        route.setDestinationCoordinates(destinationCoordinates);
        route.setDepartureCoordinates(departureCoordinates);

        ArrayList<Route> routes = new ArrayList<>();
        routes.add(route);
        newRideRequest.setRoutes(routes);

        when(rideRepository.save(newRideRequest)).thenReturn(newRideRequest);
        when(coordinatesRepository.save(departureCoordinates)).thenReturn(departureCoordinates);
        when(coordinatesRepository.save(destinationCoordinates)).thenReturn(destinationCoordinates);
        when(routeRepository.save(route)).thenReturn(route);

        rideService.save(newRideRequest);

        assertThat(newRideRequest.getRoutes()).contains(route);

    }

    @Test
    public void shouldNotWithdrawRideNotInStatusPendingOrAccepted() {
        Throwable thrown = catchThrowable(() -> {
            rideService.withdrawRide(activeRide);
        });

        assertThat(thrown).isInstanceOf(CustomExceptionWithMessage.class);
        CustomExceptionWithMessage customException = (CustomExceptionWithMessage) thrown;
        assertThat(customException.message).isEqualTo("Cannot cancel a ride that is not in status PENDING or STARTED!");
        assertThat(customException.httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void shouldWithdrawRideIfStatusPendingOrAccepted() {
        when(rideRepository.save(pendingRide)).thenReturn(pendingRide);
        when(rideRepository.save(acceptedRide)).thenReturn(acceptedRide);

        Ride withdrawnPending = rideService.withdrawRide(pendingRide);
        Ride withdrawnAccepted = rideService.withdrawRide(acceptedRide);

        assertThat(withdrawnPending.getRideStatus().toString()).isEqualTo(Ride.RIDE_STATUS.rejected.toString());
        assertThat(withdrawnAccepted.getRideStatus().toString()).isEqualTo(Ride.RIDE_STATUS.rejected.toString());

    }

    @Test
    public void shouldNotStartRideNotInAcceptedStatus() {
        Throwable thrown = catchThrowable(() -> {
            rideService.startRide(pendingRide);
        });

        assertThat(thrown).isInstanceOf(CustomExceptionWithMessage.class);
        CustomExceptionWithMessage customException = (CustomExceptionWithMessage) thrown;
        assertThat(customException.message).isEqualTo("Cannot start a ride that is not in status ACCEPTED!");
        assertThat(customException.httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void shouldStartRideInAcceptedStatus() {
        when(rideRepository.save(acceptedRide)).thenReturn(acceptedRide);

        Ride ride = rideService.startRide(acceptedRide);

        assertThat(ride.getRideStatus().toString()).isEqualTo(Ride.RIDE_STATUS.active.toString());

    }

    @Test
    public void shouldGetDriverEarliestAcceptedRide() {
        Ride earliestAcceptedRide = getDummyRide();
        earliestAcceptedRide.setRideStatus(Ride.RIDE_STATUS.accepted);
        earliestAcceptedRide.setStartTime(LocalDateTime.now().plusHours(1));

        Ride secondAcceptedRide = getDummyRide();
        secondAcceptedRide.setRideStatus(Ride.RIDE_STATUS.accepted);
        secondAcceptedRide.setStartTime(LocalDateTime.now().plusHours(2));

        Ride thirdAcceptedRide = getDummyRide();
        thirdAcceptedRide.setRideStatus(Ride.RIDE_STATUS.accepted);
        thirdAcceptedRide.setStartTime(LocalDateTime.now().plusHours(3));

        Driver driver = new Driver();
        ArrayList<Ride> rides = new ArrayList<>();
        rides.add(earliestAcceptedRide);
        rides.add(secondAcceptedRide);
        rides.add(thirdAcceptedRide);

        when(rideRepository.findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.accepted)).thenReturn(Optional.of(rides));
        Optional<List<Ride>> acceptedRides = rideRepository.findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.accepted);

        Ride earliestReturned = rideService.getDriverEarliestAcceptedRide(driver);

        assertThat(earliestReturned.getStartTime()).isBeforeOrEqualTo(earliestAcceptedRide.getStartTime());

        for (Ride ride: acceptedRides.get()) {
            assertThat(earliestAcceptedRide.getStartTime()).isBeforeOrEqualTo(ride.getStartTime());
        }

    }

    @Test
    public void shouldNotGetDriverEarliestAcceptedRideForNoAcceptedRides() {

        Driver driver = new Driver();
        ArrayList<Ride> rides = new ArrayList<>();

        when(rideRepository.findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.accepted)).thenReturn(Optional.of(rides));
        Optional<List<Ride>> acceptedRides = rideRepository.findByDriverAndRideStatus(driver, Ride.RIDE_STATUS.accepted);

        Throwable thrown = catchThrowable(() -> {
            rideService.getDriverEarliestAcceptedRide(driver);
        });

        assertThat(thrown).isInstanceOf(CustomException.class);
        CustomException customException = (CustomException) thrown;
        assertThat(customException.message).isEqualTo("Accepted ride does not exist!");
        assertThat(customException.httpStatus).isEqualTo(HttpStatus.NOT_FOUND);


    }

    @Test
    public void shouldEndRide() {
        when(rideRepository.save(any(Ride.class))).thenAnswer(invocation ->
                invocation.getArgument(0, Ride.class));
        Ride ride = rideService.endRide(activeRide);
        verify(rideRepository, times(1)).save(ride);
        assertThat(ride.getRideStatus()).isEqualTo(Ride.RIDE_STATUS.finished);
        assertThat(ride.getEndTime()).isEqualTo(activeRide.getEndTime());
        assertThat(ride.getStartTime()).isEqualTo(activeRide.getStartTime());
    }

    @Test
    public void shouldThrowExceptionPendingEndRide() {
        Exception exception = assertThrows(CustomExceptionWithMessage.class, () -> {
            rideService.endRide(pendingRide);
        });
        verify(rideRepository, never()).save(any(Ride.class));
        assertThat(pendingRide.getRideStatus()).isEqualTo(Ride.RIDE_STATUS.pending);
//        assertThat(exception.getMessage()).isEqualTo("Cannot end a ride that is not in status ACTIVE!");
    }

    @Test
    public void shouldThrowExceptionFinishedEndRide() {
        Exception exception = assertThrows(CustomExceptionWithMessage.class, () -> {
            rideService.endRide(finishedRide);
        });
        verify(rideRepository, never()).save(any(Ride.class));
        assertThat(finishedRide.getRideStatus()).isEqualTo(Ride.RIDE_STATUS.finished);
//        assertThat(exception.getMessage()).isEqualTo("Cannot end a ride that is not in status ACTIVE!");
    }

    @Test
    public void shouldThrowExceptionAcceptedEndRide() {
        Exception exception = assertThrows(CustomExceptionWithMessage.class, () -> {
            rideService.endRide(acceptedRide);
        });
        verify(rideRepository, never()).save(any(Ride.class));
        assertThat(acceptedRide.getRideStatus()).isEqualTo(Ride.RIDE_STATUS.accepted);
//        assertThat(exception.getMessage()).isEqualTo("Cannot end a ride that is not in status ACTIVE!");
    }

    @Test
    public void shouldThrowExceptionRejectedEndRide() {
        Exception exception = assertThrows(CustomExceptionWithMessage.class, () -> {
            rideService.endRide(rejectedRide);
        });
        verify(rideRepository, never()).save(any(Ride.class));
        assertThat(rejectedRide.getRideStatus()).isEqualTo(Ride.RIDE_STATUS.rejected);
//        assertThat(exception.getMessage()).isEqualTo("Cannot end a ride that is not in status ACTIVE!");
    }

    @Test
    public void shouldCancelPendingRideWithExplanation() { //pending, accepted happy flow
        when(rideRepository.save(any(Ride.class))).thenAnswer(invocation ->
                invocation.getArgument(0, Ride.class));
        Ride ride = rideService.cancelRideWithExplanation(pendingRide, REJECTION_REASON);
        verify(rideRepository, times(1)).save(ride);
        verify(rejectionRepository, times(1)).save(ride.getRejection());
        assertThat(ride.getRideStatus()).isEqualTo(Ride.RIDE_STATUS.rejected);
        assertThat(ride.getRejection().getReason()).isEqualTo(REJECTION_REASON);
    }

    @Test
    public void shouldCancelAcceptedRideWithExplanation() { //pending, accepted happy flow
        when(rideRepository.save(any(Ride.class))).thenAnswer(invocation ->
                invocation.getArgument(0, Ride.class));
        Ride ride = rideService.cancelRideWithExplanation(acceptedRide, REJECTION_REASON);
        verify(rideRepository, times(1)).save(ride);
        verify(rejectionRepository, times(1)).save(ride.getRejection());
        assertThat(ride.getRideStatus()).isEqualTo(Ride.RIDE_STATUS.rejected);
        assertThat(ride.getRejection().getReason()).isEqualTo(REJECTION_REASON);
    }

    @Test
    public void shouldThrowExceptionCancelFinishedRideWithExplanation() {
        Exception exception = assertThrows(CustomExceptionWithMessage.class, () -> {
            rideService.cancelRideWithExplanation(finishedRide, REJECTION_REASON);
        });
        verify(rideRepository, never()).save(any(Ride.class));
        verify(rejectionRepository, never()).save(any(Rejection.class));
        assertThat(finishedRide.getRideStatus()).isEqualTo(Ride.RIDE_STATUS.finished);
//        assertThat(exception.getMessage()).isEqualTo("Cannot end a ride that is not in status ACTIVE!");
    }

    @Test
    public void shouldThrowExceptionCancelActiveRideWithExplanation() {
        Exception exception = assertThrows(CustomExceptionWithMessage.class, () -> {
            rideService.cancelRideWithExplanation(activeRide, REJECTION_REASON);
        });
        verify(rideRepository, never()).save(any(Ride.class));
        verify(rejectionRepository, never()).save(any(Rejection.class));
        assertThat(activeRide.getRideStatus()).isEqualTo(Ride.RIDE_STATUS.active);
//        assertThat(exception.getMessage()).isEqualTo("Caption.getMessage()).isEqualTo("Cannot end a ride that is not in status ACTIVE!");
    }

    @Test
    public void shouldThrowExceptionCancelRejectedRideWithExplanation() {
        Exception exception = assertThrows(CustomExceptionWithMessage.class, () -> {
            rideService.cancelRideWithExplanation(rejectedRide, REJECTION_REASON);
        });
        verify(rideRepository, never()).save(any(Ride.class));
        verify(rejectionRepository, never()).save(any(Rejection.class));
        assertThat(rejectedRide.getRideStatus()).isEqualTo(Ride.RIDE_STATUS.rejected);
    }

    @Test
    public void shouldBookRide() {
        VehicleType vehicleType = new VehicleType(0L, Vehicle.VEHICLE_TYPE.standard, 0);
        Vehicle dummyVehicle = new Vehicle(1L, pendingRide.getDriver(), "model", "plate", 5, new Coordinates(1L, 10.0, 10.0, "adres"), vehicleType, true, true);
        makeValidRideRequest(dummyVehicle, false);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(dummyVehicle);
        when(vehicleTypeRepository.getByName(dummyVehicle.getVehicleType().getName())).thenReturn(vehicleType);
        when(vehicleRepository.findByVehicleType(vehicleType)).thenReturn(vehicles);
        boolean result = rideService.isBookableRide(pendingRide);
        verify(vehicleTypeRepository, times(2)).getByName(dummyVehicle.getVehicleType().getName());
        verify(vehicleRepository, times(1)).findByVehicleType(vehicleType);
        verify(passengerRepository, times(pendingRide.getPassengers().size())).findByEmail(anyString());
        verify(rideRepository, times(1)).findByPassengersContainingAndRideStatusIn(any(Passenger.class), eq(statuses));
        verify(rideRepository, times(1)).findAllByRideStatusInAndDriver(eq(statuses), any(Driver.class));
        verify(workingTimeRepository, times(1)).findByDriverAndStartTimeGreaterThanEqual(eq(pendingRide.getDriver()), any(LocalDateTime.class));
        assertThat(result).isTrue();
        assertThat(pendingRide.getRejection()).isNotNull();
        assertThat(pendingRide.getRideStatus()).isEqualTo(Ride.RIDE_STATUS.pending);
        assertThat(pendingRide.getDriver().getId()).isEqualTo(2);
    }

    @Test
    public void shouldBookRideWithMoreData() {
        VehicleType vehicleType = new VehicleType(0L, Vehicle.VEHICLE_TYPE.standard, 0);
        Vehicle dummyVehicle = new Vehicle(1L, pendingRide.getDriver(), "model", "plate", 5, new Coordinates(1L, 10.0, 10.0, "adres"), vehicleType, true, true);
        Vehicle notValidVehicle = new Vehicle(1L, pendingRide.getDriver(), "model", "plate", 5, new Coordinates(1L, 10.0, 10.0, "adres"), vehicleType, false, true);
        Driver secondDriver = new Driver();
        secondDriver.setId(4L);
        //////////////////
        secondDriver.setActiveFlag(true);
        /////////////
        Vehicle anotherValidVehicle = new Vehicle(1L, secondDriver, "model", "plate", 5, new Coordinates(1L, 10.0, 10.0, "adres"), vehicleType, true, true);
        makeValidRideRequest(dummyVehicle, true);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(dummyVehicle);
        vehicles.add(notValidVehicle);
        vehicles.add(anotherValidVehicle);
        Passenger passenger = new Passenger();
        passenger.setId(0L);
        Passenger passenger2 = new Passenger();
        passenger2.setId(1L);
        pendingRide.setPassengers(Arrays.asList(passenger, passenger2));
        ArrayList<Ride> rides = new ArrayList<>();
        rides.add(pendingRide);
        when(vehicleTypeRepository.getByName(dummyVehicle.getVehicleType().getName())).thenReturn(vehicleType);
        when(vehicleRepository.findByVehicleType(vehicleType)).thenReturn(vehicles);
        when(rideRepository.findAllByRideStatusInAndDriver(statuses, pendingRide.getDriver())).thenReturn(rides);
        boolean result = rideService.isBookableRide(pendingRide);
        verify(vehicleTypeRepository, times(2)).getByName(dummyVehicle.getVehicleType().getName());
        verify(vehicleRepository, times(1)).findByVehicleType(vehicleType);
        verify(passengerRepository, times(pendingRide.getPassengers().size())).findByEmail(anyString());
        verify(rideRepository, times(1)).findByPassengersContainingAndRideStatusIn(any(Passenger.class), eq(statuses));
        verify(rideRepository, times(2)).findAllByRideStatusInAndDriver(eq(statuses), any(Driver.class));
        verify(workingTimeRepository, times(1)).findByDriverAndStartTimeGreaterThanEqual(eq(pendingRide.getDriver()), any(LocalDateTime.class));
        assertThat(result).isTrue();
        assertThat(pendingRide.getRejection()).isNotNull();
        assertThat(pendingRide.getRideStatus()).isEqualTo(Ride.RIDE_STATUS.pending);
        assertThat(pendingRide.getDriver().getId()).isEqualTo(4);
    }

    @Test
    public void shouldNotBookRidePassengerHasPending() {
        VehicleType vehicleType = new VehicleType(0L, Vehicle.VEHICLE_TYPE.standard, 0);
        Vehicle dummyVehicle = new Vehicle(1L, pendingRide.getDriver(), "model", "plate", 5, new Coordinates(1L, 10.0, 10.0, "adres"), vehicleType, true, true);
        makeValidRideRequest(dummyVehicle, false);
        doReturn(Optional.of(activeRide)).when(rideRepository).findByPassengersContainingAndRideStatusIn(any(Passenger.class), eq(statuses));
        assertThrows(CustomExceptionWithMessage.class, () -> {
            rideService.isBookableRide(pendingRide);
        });
        verify(vehicleTypeRepository, never()).getByName(dummyVehicle.getVehicleType().getName());
        verify(vehicleRepository, never()).findByVehicleType(vehicleType);
        verify(passengerRepository, never()).findByEmail(anyString());
        verify(rideRepository, times(1)).findByPassengersContainingAndRideStatusIn(any(Passenger.class), eq(statuses));
        verify(rideRepository, never()).findAllByRideStatusInAndDriver(eq(statuses), any(Driver.class));
        verify(workingTimeRepository, never()).findByDriverAndStartTimeGreaterThanEqual(eq(pendingRide.getDriver()), any(LocalDateTime.class));
    }

    @Test
    public void shouldNotBookRideNoVehicles() {
        VehicleType vehicleType = new VehicleType(0L, Vehicle.VEHICLE_TYPE.standard, 0);
        Vehicle dummyVehicle = new Vehicle(1L, pendingRide.getDriver(), "model", "plate", 5, new Coordinates(1L, 10.0, 10.0, "adres"), vehicleType, true, true);
        makeValidRideRequest(dummyVehicle, false);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        when(vehicleTypeRepository.getByName(dummyVehicle.getVehicleType().getName())).thenReturn(vehicleType);
        when(vehicleRepository.findByVehicleType(vehicleType)).thenReturn(vehicles);
        boolean result = rideService.isBookableRide(pendingRide);
        verify(vehicleTypeRepository, times(1)).getByName(dummyVehicle.getVehicleType().getName());
        verify(vehicleRepository, times(1)).findByVehicleType(vehicleType);
        verify(passengerRepository, never()).findByEmail(anyString());
        verify(rideRepository, times(1)).findByPassengersContainingAndRideStatusIn(any(Passenger.class), eq(statuses));
        verify(rideRepository, never()).findAllByRideStatusInAndDriver(eq(statuses), any(Driver.class));
        verify(workingTimeRepository, never()).findByDriverAndStartTimeGreaterThanEqual(eq(pendingRide.getDriver()), any(LocalDateTime.class));
        assertThat(result).isFalse();
    }

    @Test
    public void shouldNotBookRideNoAppropriate() {
        VehicleType vehicleType = new VehicleType(0L, Vehicle.VEHICLE_TYPE.standard, 0);
        Vehicle dummyVehicle = new Vehicle(1L, pendingRide.getDriver(), "model", "plate", 5, new Coordinates(1L, 10.0, 10.0, "adres"), vehicleType, false, false);
        makeValidRideRequest(dummyVehicle, true);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(dummyVehicle);
        when(vehicleTypeRepository.getByName(dummyVehicle.getVehicleType().getName())).thenReturn(vehicleType);
        when(vehicleRepository.findByVehicleType(vehicleType)).thenReturn(vehicles);
        boolean result = rideService.isBookableRide(pendingRide);
        verify(vehicleTypeRepository, times(1)).getByName(dummyVehicle.getVehicleType().getName());
        verify(vehicleRepository, times(1)).findByVehicleType(vehicleType);
        verify(passengerRepository, never()).findByEmail(anyString());
        verify(rideRepository, times(1)).findByPassengersContainingAndRideStatusIn(any(Passenger.class), eq(statuses));
        verify(rideRepository, never()).findAllByRideStatusInAndDriver(eq(statuses), any(Driver.class));
        verify(workingTimeRepository, never()).findByDriverAndStartTimeGreaterThanEqual(eq(pendingRide.getDriver()), any(LocalDateTime.class));
        assertThat(result).isFalse();
    }

    @Test
    public void shouldNotBookRideDriverBlocked() {
        pendingRide.getDriver().setBlockedFlag(true);
        VehicleType vehicleType = new VehicleType(0L, Vehicle.VEHICLE_TYPE.standard, 0);
        Vehicle dummyVehicle = new Vehicle(1L, pendingRide.getDriver(), "model", "plate", 5, new Coordinates(1L, 10.0, 10.0, "adres"), vehicleType, true, true);
        makeValidRideRequest(dummyVehicle, false);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(dummyVehicle);
        when(vehicleTypeRepository.getByName(dummyVehicle.getVehicleType().getName())).thenReturn(vehicleType);
        when(vehicleRepository.findByVehicleType(vehicleType)).thenReturn(vehicles);
        boolean result = rideService.isBookableRide(pendingRide);
        verify(vehicleTypeRepository, times(1)).getByName(dummyVehicle.getVehicleType().getName());
        verify(vehicleRepository, times(1)).findByVehicleType(vehicleType);
        verify(passengerRepository, never()).findByEmail(anyString());
        verify(rideRepository, times(1)).findByPassengersContainingAndRideStatusIn(any(Passenger.class), eq(statuses));
        verify(rideRepository, times(1)).findAllByRideStatusInAndDriver(eq(statuses), any(Driver.class));
        verify(workingTimeRepository, times(1)).findByDriverAndStartTimeGreaterThanEqual(eq(pendingRide.getDriver()), any(LocalDateTime.class));
        assertThat(result).isFalse();
    }

    @Test
    public void shouldNotBookRideDriverTired() {
        VehicleType vehicleType = new VehicleType(0L, Vehicle.VEHICLE_TYPE.standard, 0);
        Vehicle dummyVehicle = new Vehicle(1L, pendingRide.getDriver(), "model", "plate", 5, new Coordinates(1L, 10.0, 10.0, "adres"), vehicleType, true, true);
        makeValidRideRequest(dummyVehicle, false);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(dummyVehicle);
        ArrayList<WorkingTime> workingTimes = new ArrayList<>();
        workingTimes.add(new WorkingTime(1L, LocalDateTime.now().minusHours(8), LocalDateTime.now(), pendingRide.getDriver()));
        workingTimes.add(new WorkingTime(1L, LocalDateTime.now().minusMinutes(1), null, pendingRide.getDriver()));
        when(vehicleTypeRepository.getByName(dummyVehicle.getVehicleType().getName())).thenReturn(vehicleType);
        when(vehicleRepository.findByVehicleType(vehicleType)).thenReturn(vehicles);
        when(workingTimeRepository.findByDriverAndStartTimeGreaterThanEqual(eq(pendingRide.getDriver()), any(LocalDateTime.class))).thenReturn(workingTimes);
        boolean result = rideService.isBookableRide(pendingRide);
        verify(vehicleTypeRepository, times(1)).getByName(dummyVehicle.getVehicleType().getName());
        verify(vehicleRepository, times(1)).findByVehicleType(vehicleType);
        verify(passengerRepository, never()).findByEmail(anyString());
        verify(rideRepository, times(1)).findByPassengersContainingAndRideStatusIn(any(Passenger.class), eq(statuses));
        verify(rideRepository, times(1)).findAllByRideStatusInAndDriver(eq(statuses), any(Driver.class));
        verify(workingTimeRepository, times(1)).findByDriverAndStartTimeGreaterThanEqual(eq(pendingRide.getDriver()), any(LocalDateTime.class));
        assertThat(result).isFalse();
    }

    private void makeValidRideRequest(Vehicle dummyVehicle, boolean flagsWanted) {
        pendingRide.getDriver().setActiveFlag(true);
        pendingRide.getDriver().setId(2L);
        pendingRide.getDriver().setVehicle(dummyVehicle);
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        pendingRide.setBabyFlag(flagsWanted);
        pendingRide.setPetsFlag(flagsWanted);
        pendingRide.setPassengers(Arrays.asList(passenger));
        pendingRide.setEstimatedTimeMinutes(15);
        pendingRide.setPrice(1500);
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(new Route(1L, new Coordinates(), new Coordinates(), 10.0, 1, null));
        pendingRide.setRoutes(routes);
    }
}
