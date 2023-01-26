package com.ISSUberTim10.ISSUberTim10.ride.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.ride.Report;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.dto.StatisticsDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IStatisticService {
    ArrayList<Ride> getAllByStartTimeBetweenAndDriver(LocalDateTime fromDate, LocalDateTime toDate, Driver driver);

    Report makeReportForRideNum(LocalDateTime fromDate, LocalDateTime toDate, Driver driver, ArrayList<Ride> rides);

    Report makeReportForDistance(LocalDateTime fromDate, LocalDateTime toDate, Driver driver, ArrayList<Ride> rides);

    Report makeReportForMoney(LocalDateTime fromDate, LocalDateTime toDate, Driver driver, ArrayList<Ride> rides);

    StatisticsDTO makeStatisticsForMonth(Driver driver);

    StatisticsDTO makeStatisticsForYear(Driver driver);

    StatisticsDTO makeStatisticsForDay(Driver driver);


    ArrayList<Ride> getAllByStartTimeBetweenAndPassenger(LocalDateTime fromDate, LocalDateTime toDate, Passenger passenger);


}
