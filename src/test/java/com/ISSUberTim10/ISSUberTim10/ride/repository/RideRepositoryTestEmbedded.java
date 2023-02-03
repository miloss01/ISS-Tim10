package com.ISSUberTim10.ISSUberTim10.ride.repository;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
//@RunWith(SpringRunner.class)
//@Sql(scripts = {"classpath:test.sql"})
//@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
//@ActiveProfiles("profile")
public class RideRepositoryTestEmbedded {

    @Autowired
    private RideRepository rideRepository;

    @Test
    public void shouldFindAllRidesWithoutPageable() {
        Page<Ride> rides = rideRepository.findAll(Pageable.unpaged());
        assertThat(rides.getContent().size()).isEqualTo(8);
    }

    @Test
    public void shouldFindAllRidesWithPageable() {
        Pageable pageable = PageRequest.of(1, 2, Sort.by("id"));
        Page<Ride> rides = rideRepository.findAll(pageable);
        assertThat(rides.getContent().size()).isEqualTo(2);
        assertThat(rides.getContent().get(0).getId()).isEqualTo(3);
        assertThat(rides.getContent().get(1).getId()).isEqualTo(4);
    }

    @Test
    public void shouldFindAllRidesByDriverAndWithoutPageable() {
        Driver driver = new Driver();
        driver.setId(2L);
        Page<Ride> rides = rideRepository.findAllByDriver(Pageable.unpaged(), driver);
        for (Ride ride : rides.getContent())
            assertThat(ride.getDriver().getId()).isEqualTo(driver.getId());
    }

    @Test
    public void shouldFindAllRidesByDriverAndWithPageable() {
        Driver driver = new Driver();
        driver.setId(2L);
        Pageable pageable = PageRequest.of(1, 2, Sort.by("id"));
        Page<Ride> rides = rideRepository.findAllByDriver(pageable, driver);
        assertThat(rides.getContent().size()).isEqualTo(1);
        for (Ride ride : rides.getContent())
            assertThat(ride.getDriver().getId()).isEqualTo(driver.getId());
    }

    @Test
    public void shouldNotFindAnyRidesWithGivenDriver() {
        Driver driver = new Driver();
        driver.setId(10L);
        Page<Ride> rides = rideRepository.findAllByDriver(Pageable.unpaged(), driver);
        assertThat(rides.getContent().size()).isEqualTo(0);
    }

    @Test
    public void shouldFindAllRidesWithGivenPassengerAndWithoutPageable() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        Page<Ride> rides = rideRepository.findAllByPassengersContaining(Pageable.unpaged(), passenger);
        assertThat(rides.getContent().size()).isEqualTo(3);
        for (Ride ride : rides.getContent()) {
            Boolean found = false;
            for (Passenger ridePassenger : ride.getPassengers())
                if (ridePassenger.getId() == passenger.getId())
                    found = true;
            assertThat(found).isEqualTo(true);
        }
    }

    @Test
    public void shouldFindAllRidesWithGivenPassengerAndWithPageable() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        Pageable pageable = PageRequest.of(1, 2, Sort.by("id"));
        Page<Ride> rides = rideRepository.findAllByPassengersContaining(pageable, passenger);
        assertThat(rides.getContent().size()).isEqualTo(1);
        for (Ride ride : rides.getContent()) {
            Boolean found = false;
            for (Passenger ridePassenger : ride.getPassengers())
                if (ridePassenger.getId() == passenger.getId())
                    found = true;
            assertThat(found).isEqualTo(true);
        }
    }

    @Test
    public void shouldNotFindAnyRidesWithGivenPassengerAndWithoutPageable() {
        Passenger passenger = new Passenger();
        passenger.setId(10L);
        Page<Ride> rides = rideRepository.findAllByPassengersContaining(Pageable.unpaged(), passenger);
        assertThat(rides.getContent().size()).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2026-04-12 07:03:24,2026-06-12 07:03:24,3", "2026-04-13 07:03:23,2026-06-11 07:03:25,1"})
    public void shouldFindAllRidesWithGivenPassengerAndDate(String params) {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        String[] data = params.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(data[0], formatter);
        LocalDateTime end = LocalDateTime.parse(data[1], formatter);
        ArrayList<Ride> rides = rideRepository.findAllByStartTimeBetweenAndPassengersContaining(start, end, passenger);
        assertThat(rides.size()).isEqualTo(Integer.parseInt(data[2]));
    }

    @Test
    public void shouldNotFindRidesWithGivenPassengerAndDate() {
        Passenger passenger = new Passenger();
        passenger.setId(10L);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        ArrayList<Ride> rides = rideRepository.findAllByStartTimeBetweenAndPassengersContaining(start, end, passenger);
        assertThat(rides.size()).isEqualTo(0);
    }

    @Test
    public void shouldNotFindRidesWithInvalidPassengerAndDate() {
        Passenger passenger = null;
        LocalDateTime start = LocalDateTime.now();
        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            rideRepository.findAllByStartTimeBetweenAndPassengersContaining(start, start, passenger);
        });
//        ArrayList<Ride> rides = rideRepository.findAllByStartTimeBetweenAndPassengersContaining(start, start, passenger);
//        assertThat(rides.size()).isEqualTo(0);
    }

    @Test
    public void shouldFindAllRidesForDriver() {
        Driver driver = new Driver();
        driver.setId(2L);
        ArrayList<Ride> rides = rideRepository.findAllByDriver(driver);
        assertThat(rides.size()).isEqualTo(3);
    }

    @Test
    public void shouldNotFindRidesForDriver() {
        Driver driver = new Driver();
        driver.setId(2342L);
        ArrayList<Ride> rides = rideRepository.findAllByDriver(driver);
        assertThat(rides.size()).isEqualTo(0);
    }


    @ParameterizedTest
    @ValueSource(strings = {"2026-04-12 07:03:24,2026-06-12 07:03:24,3", "2026-04-13 07:03:23,2026-06-11 07:03:25,1"})
    public void shouldFindAllRidesWithGivenDriverAndDate(String params) {
        Driver driver = new Driver();
        driver.setId(2L);
        String[] data = params.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(data[0], formatter);
        LocalDateTime end = LocalDateTime.parse(data[1], formatter);
        ArrayList<Ride> rides = rideRepository.findAllByStartTimeBetweenAndDriver(start, end, driver);
        assertThat(rides.size()).isEqualTo(Integer.parseInt(data[2]));
    }

    @Test
    public void shouldNotFindRidesWithGivenDriverAndDate() {
        Driver driver = new Driver();
        driver.setId(2L);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        ArrayList<Ride> rides = rideRepository.findAllByStartTimeBetweenAndDriver(start, end, driver);
        assertThat(rides.size()).isEqualTo(0);
    }

    @Test
    public void shouldNotFindRidesWithInvalidDriverAndDate() {
        Driver driver = null;
        LocalDateTime start = LocalDateTime.now();
        ArrayList<Ride> rides = rideRepository.findAllByStartTimeBetweenAndDriver(start, start, driver);
        assertThat(rides.size()).isEqualTo(0);
    }

    @Test
    public void shouldFindAllRidesWithGivenDriverAndStatuses() {
        Driver driver = new Driver();
        driver.setId(2L);
        ArrayList<Ride.RIDE_STATUS> statuses = new ArrayList<>();
        statuses.add(Ride.RIDE_STATUS.active);
        statuses.add(Ride.RIDE_STATUS.accepted);
        statuses.add(Ride.RIDE_STATUS.pending);
        ArrayList<Ride> rides = rideRepository.findAllByRideStatusInAndDriver(statuses, driver);
        assertThat(rides.size()).isEqualTo(3);
    }

    @Test
    public void shouldNotFindRidesWithGivenDriverAndStatuses() {
        Driver driver = new Driver();
        driver.setId(4L);
        ArrayList<Ride.RIDE_STATUS> statuses = new ArrayList<>();
        statuses.add(Ride.RIDE_STATUS.active);
        statuses.add(Ride.RIDE_STATUS.accepted);
        statuses.add(Ride.RIDE_STATUS.pending);
        ArrayList<Ride> rides = rideRepository.findAllByRideStatusInAndDriver(statuses, driver);
        assertThat(rides.size()).isEqualTo(0);
    }

    @Test
    public void shouldNotFindRidesWithInvalidDriverAndStatuses() {
        Driver driver = null;
        ArrayList<Ride.RIDE_STATUS> statuses = new ArrayList<>();
        ArrayList<Ride> rides = rideRepository.findAllByRideStatusInAndDriver(statuses, driver);
        assertThat(rides.size()).isEqualTo(0);
    }

    @Test
    public void shouldThrowErrorWithInvalidDriverAndStatuses() {
        Driver driver = null;
        ArrayList<Ride.RIDE_STATUS> statuses = null;
        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            rideRepository.findAllByRideStatusInAndDriver(statuses, driver);
        });
    }

    //=============================================================
    @Test
    public void shouldFindRideWithGivenPassengerAndStatuses() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        ArrayList<Ride.RIDE_STATUS> statuses = new ArrayList<>();
        statuses.add(Ride.RIDE_STATUS.active);
//        statuses.add(Ride.RIDE_STATUS.accepted);
//        statuses.add(Ride.RIDE_STATUS.pending);
        Optional<Ride> found= rideRepository.findByPassengersContainingAndRideStatusIn(passenger, statuses);
        assertThat(found.isPresent()).isTrue();
    }

    @Test
    public void shouldNotFindRideWithGivenPassengerAndStatuses() {
        Passenger passenger = new Passenger();
        passenger.setId(8L);
        ArrayList<Ride.RIDE_STATUS> statuses = new ArrayList<>();
        statuses.add(Ride.RIDE_STATUS.active);
        statuses.add(Ride.RIDE_STATUS.accepted);
        statuses.add(Ride.RIDE_STATUS.pending);
        Optional<Ride> found= rideRepository.findByPassengersContainingAndRideStatusIn(passenger, statuses);
        assertThat(found.isPresent()).isFalse();
    }

    @Test
    public void shouldThrowErrorWithInvalidPassengerAndStatuses() {
        Passenger passenger = null;
        ArrayList<Ride.RIDE_STATUS> statuses = null;
        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            rideRepository.findByPassengersContainingAndRideStatusIn(passenger, statuses);
        });
    }

}
