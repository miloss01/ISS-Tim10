package com.ISSUberTim10.ISSUberTim10.ride.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IPassengerService;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IDriverService;
import com.ISSUberTim10.ISSUberTim10.helper.StringFormatting;
import com.ISSUberTim10.ISSUberTim10.ride.Report;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.ReportDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.StatisticsDTO;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRideService;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "http://localhost:4200")
@EnableScheduling
@Validated
public class StatisticsController {

    @Autowired
    IStatisticService statisticService;
    @Autowired
    IDriverService driverService;
    @Autowired
    IPassengerService passengerService;

    @GetMapping(value = "/report-ride-number/{driverId}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> getRideNumberReport(@PathVariable Integer driverId,
                                                         @PathVariable String from,
                                                         @PathVariable String to) {
        System.out.println("USAO");
        Driver driver = driverService.getById(Long.valueOf(driverId));
        LocalDateTime fromDate = LocalDateTime.parse(from, StringFormatting.dateTimeFormatter);
        LocalDateTime toDate = LocalDateTime.parse(to, StringFormatting.dateTimeFormatter);
        ArrayList<Ride> rides = statisticService.getAllByStartTimeBetweenAndDriver(fromDate, toDate, driver);
        Report report = statisticService.makeReportForRideNum(fromDate, toDate, driver, rides);
        System.out.println("KRAJ");
        return new ResponseEntity<>(
                new ReportDTO(report),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/passenger-report-ride-number/{passengerId}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> getPassengerRideNumberReport(@PathVariable Integer passengerId,
                                                         @PathVariable String from,
                                                         @PathVariable String to) {
        System.out.println("USAO");
        Passenger passenger = passengerService.getPassenger(Long.valueOf(passengerId));
        LocalDateTime fromDate = LocalDateTime.parse(from, StringFormatting.dateTimeFormatter);
        LocalDateTime toDate = LocalDateTime.parse(to, StringFormatting.dateTimeFormatter);
        ArrayList<Ride> rides = statisticService.getAllByStartTimeBetweenAndPassenger(fromDate, toDate, passenger);
        Report report = statisticService.makeReportForRideNum(fromDate, toDate, null, rides);
        System.out.println("KRAJ");
        return new ResponseEntity<>(
                new ReportDTO(report),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/report-distance/{driverId}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> getDistanceReport(@PathVariable Integer driverId,
                                                       @PathVariable String from,
                                                       @PathVariable String to) {

        Driver driver = driverService.getById(Long.valueOf(driverId));
        LocalDateTime fromDate = LocalDateTime.parse(from, StringFormatting.dateTimeFormatter);
        LocalDateTime toDate = LocalDateTime.parse(to, StringFormatting.dateTimeFormatter);
        ArrayList<Ride> rides = statisticService.getAllByStartTimeBetweenAndDriver(fromDate, toDate, driver);
        Report report = statisticService.makeReportForDistance(fromDate, toDate, driver, rides);
        return new ResponseEntity<>(
                new ReportDTO(report),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/passenger-report-distance/{passengerId}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> getPassengerDistanceReport(@PathVariable Integer passengerId,
                                                       @PathVariable String from,
                                                       @PathVariable String to) {

        Passenger passenger = passengerService.getPassenger(Long.valueOf(passengerId));
        LocalDateTime fromDate = LocalDateTime.parse(from, StringFormatting.dateTimeFormatter);
        LocalDateTime toDate = LocalDateTime.parse(to, StringFormatting.dateTimeFormatter);
        ArrayList<Ride> rides = statisticService.getAllByStartTimeBetweenAndPassenger(fromDate, toDate, passenger);
        Report report = statisticService.makeReportForDistance(fromDate, toDate, null, rides);
        return new ResponseEntity<>(
                new ReportDTO(report),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/report-money/{driverId}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> getMoneyReport(@PathVariable Integer driverId,
                                                    @PathVariable String from,
                                                    @PathVariable String to) {

        Driver driver = driverService.getById(Long.valueOf(driverId));
        LocalDateTime fromDate = LocalDateTime.parse(from, StringFormatting.dateTimeFormatter);
        LocalDateTime toDate = LocalDateTime.parse(to, StringFormatting.dateTimeFormatter);
        ArrayList<Ride> rides = statisticService.getAllByStartTimeBetweenAndDriver(fromDate, toDate, driver);
        Report report = statisticService.makeReportForMoney(fromDate, toDate, driver, rides);
        return new ResponseEntity<>(
                new ReportDTO(report),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/passenger-report-money/{passengerId}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> getPassengerMoneyReport(@PathVariable Integer passengerId,
                                                    @PathVariable String from,
                                                    @PathVariable String to) {

        Passenger passenger = passengerService.getPassenger(Long.valueOf(passengerId));
        LocalDateTime fromDate = LocalDateTime.parse(from, StringFormatting.dateTimeFormatter);
        LocalDateTime toDate = LocalDateTime.parse(to, StringFormatting.dateTimeFormatter);
        ArrayList<Ride> rides = statisticService.getAllByStartTimeBetweenAndPassenger(fromDate, toDate, passenger);
        Report report = statisticService.makeReportForMoney(fromDate, toDate, null, rides);
        return new ResponseEntity<>(
                new ReportDTO(report),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/statistic-month/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatisticsDTO> getMonthStatistics(@PathVariable Integer driverId) {

        Driver driver = driverService.getById(Long.valueOf(driverId));
        StatisticsDTO statistics = statisticService.makeStatisticsForMonth(driver);
        return new ResponseEntity<>(
                statistics,
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/statistic-year/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatisticsDTO> getYearStatistics(@PathVariable Integer driverId) {

        Driver driver = driverService.getById(Long.valueOf(driverId));
        StatisticsDTO statistics = statisticService.makeStatisticsForYear(driver);
        return new ResponseEntity<>(
                statistics,
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/statistic-day/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatisticsDTO> getDayStatistics(@PathVariable Integer driverId) {

        Driver driver = driverService.getById(Long.valueOf(driverId));
        StatisticsDTO statistics = statisticService.makeStatisticsForDay(driver);
        return new ResponseEntity<>(
                statistics,
                HttpStatus.OK
        );
    }
}
