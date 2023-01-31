package com.ISSUberTim10.ISSUberTim10.ride.repository;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(rides.getContent().size()).isEqualTo(5);
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

}
