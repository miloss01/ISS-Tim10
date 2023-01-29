package com.ISSUberTim10.ISSUberTim10.ride.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.WorkingTime;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.WorkingTimeRepository;
import com.ISSUberTim10.ISSUberTim10.ride.Report;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import com.ISSUberTim10.ISSUberTim10.ride.Route;
import com.ISSUberTim10.ISSUberTim10.ride.dto.StatisticsDTO;
import com.ISSUberTim10.ISSUberTim10.ride.repository.RideRepository;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticService implements IStatisticService {
    @Autowired
    RideRepository rideRepository;
    @Autowired
    WorkingTimeRepository workingTimeRepository;

    @Override
    public ArrayList<Ride> getAllByStartTimeBetweenAndDriver(LocalDateTime fromDate, LocalDateTime toDate, Driver driver) {
        return rideRepository.findAllByStartTimeBetweenAndDriver(fromDate, toDate, driver);
    }

    @Override
    public ArrayList<Ride> getAllByStartTimeBetweenAndPassenger(LocalDateTime fromDate, LocalDateTime toDate, Passenger passenger) {
        return rideRepository.findAllByStartTimeBetweenAndPassengersContaining(fromDate, toDate, passenger);
    }

    @Override
    public Report makeReportForRideNum(LocalDateTime fromDate, LocalDateTime toDate, Driver driver, ArrayList<Ride> rides) {
//        ArrayList<Ride> rides = rideRepository.findAllByStartTimeBetweenAndDriver(fromDate, toDate, driver);
        //double total = 0;
        HashMap<LocalDate, Double> values = new HashMap<>();
        for (Ride ride: rides) {
            LocalDate day = LocalDate.of(ride.getStartTime().getYear(), ride.getStartTime().getMonth(), ride.getStartTime().getDayOfMonth());
            if (!values.containsKey(day)) values.put(day, 0.0);
            values.put(day, values.get(day) + 1);
        }
        double total = rides.size();
        double average = total/values.size();
        return new Report(values, total, average);
    }

    @Override
    public Report makeReportForDistance(LocalDateTime fromDate, LocalDateTime toDate, Driver driver, ArrayList<Ride> rides) {
//        ArrayList<Ride> rides = rideRepository.findAllByStartTimeBetweenAndDriver(fromDate, toDate, driver);
        double total = 0;
        HashMap<LocalDate, Double> values = new HashMap<>();
        for (Ride ride: rides) {
            Route route = ride.getRoutes().iterator().next();
            LocalDate day = LocalDate.of(ride.getStartTime().getYear(), ride.getStartTime().getMonth(), ride.getStartTime().getDayOfMonth());
            if (!values.containsKey(day)) values.put(day, 0.0);
            values.put(day, values.get(day) + route.getMileage());
            total += route.getMileage();
        }
        double average = total/values.size();
        return new Report(values, total, average);
    }

    @Override
    public Report makeReportForMoney(LocalDateTime fromDate, LocalDateTime toDate, Driver driver, ArrayList<Ride> rides) {
//        ArrayList<Ride> rides = rideRepository.findAllByStartTimeBetweenAndDriver(fromDate, toDate, driver);
        double total = 0;
        HashMap<LocalDate, Double> values = new HashMap<>();
        for (Ride ride: rides) {
            LocalDate day = LocalDate.of(ride.getStartTime().getYear(), ride.getStartTime().getMonth(), ride.getStartTime().getDayOfMonth());
            if (!values.containsKey(day)) values.put(day, 0.0);
            values.put(day, values.get(day) + ride.getPrice());
            total += ride.getPrice();
        }
        double average = total/values.size();
        return new Report(values, total, average);
    }

    @Override
    public StatisticsDTO makeStatisticsForMonth(Driver driver) {
        ArrayList<Ride> rides = rideRepository.findAllByDriver(driver);
        double kilometers = getAverageKmMonth(rides);
        double acceptance = getAverageAcceptanceMonth(rides);
        double money = getAverageIncomeMonth(rides);
        double workingTimes = getAverageWorkingHourMonth(driver);
        return new StatisticsDTO(acceptance, workingTimes, kilometers, money);
    }

    @Override
    public StatisticsDTO makeStatisticsForYear(Driver driver) {
        ArrayList<Ride> rides = rideRepository.findAllByDriver(driver);
        double kilometers = getAverageKmYear(rides);
        double acceptance = getAverageAcceptanceYear(rides);
        double money = getAverageIncomeYear(rides);
        double workingTimes = getAverageWorkingHourYear(driver);
        return new StatisticsDTO(acceptance, workingTimes, kilometers, money);
    }

    private double getAverageIncomeYear(ArrayList<Ride> rides) {
        double total = 0;
        HashMap<Integer, Double> values = new HashMap<>();
        for (Ride ride: rides) {
            Integer date = ride.getStartTime().getYear();
            if (!values.containsKey(date)) values.put(date, 0.0);
            total += ride.getPrice();
            values.put(date, values.get(date)+ ride.getPrice());
        }
        return total/ (double) values.size();
    }

    private double getAverageAcceptanceYear(ArrayList<Ride> rides) {
        HashMap<Integer, Integer> totalRides = new HashMap<>();
        HashMap<Integer, Integer> acceptedRides = new HashMap<>();
        for (Ride ride: rides) {
            Integer date = ride.getStartTime().getYear();
            if (!totalRides.containsKey(date)) {
                totalRides.put(date, 0);
                acceptedRides.put(date, 0);
            }
            totalRides.put(date, totalRides.get(date)+ 1);
            if (ride.getRideStatus() != Ride.RIDE_STATUS.rejected) acceptedRides.put(date, acceptedRides.get(date) + 1);
        }
        double total = 0;
        for (Map.Entry<Integer, Integer> set: totalRides.entrySet()) {
            total += 100 * acceptedRides.get(set.getKey())/(double) set.getValue();
        }
        return total/ (double) totalRides.size();
    }

    private double getAverageKmYear(ArrayList<Ride> rides) {
        double total = 0;
        HashMap<Integer, Double> values = new HashMap<>();
        for (Ride ride: rides) {
            Route route = ride.getRoutes().iterator().next();
            Integer date = ride.getStartTime().getYear();
            if (!values.containsKey(date)) values.put(date, 0.0);
            total += route.getMileage();
            values.put(date, values.get(date)+route.getMileage());
        }
        return total/ (double) values.size();
    }

    @Override
    public StatisticsDTO makeStatisticsForDay(Driver driver) {
        ArrayList<Ride> rides = rideRepository.findAllByDriver(driver);
        double kilometers = getAverageKmDay(rides);
        double acceptance = getAverageAcceptanceDay(rides);
        double money = getAverageIncomeDay(rides);
        double workingTimes = getAverageWorkingHourDay(driver);
        return new StatisticsDTO(acceptance, workingTimes, kilometers, money);
    }

    private double getAverageIncomeDay(ArrayList<Ride> rides) {
        double total = 0;
        HashMap<LocalDate, Double> values = new HashMap<>();
        for (Ride ride: rides) {
            LocalDate date = LocalDate.of(ride.getStartTime().getYear(), ride.getStartTime().getMonth(), ride.getStartTime().getDayOfMonth());
            if (!values.containsKey(date)) values.put(date, 0.0);
            total += ride.getPrice();
            values.put(date, values.get(date)+ ride.getPrice());
        }
        return total/ (double) values.size();
    }

    private double getAverageAcceptanceDay(ArrayList<Ride> rides) {
        HashMap<LocalDate, Integer> totalRides = new HashMap<>();
        HashMap<LocalDate, Integer> acceptedRides = new HashMap<>();
        for (Ride ride: rides) {
            LocalDate date = LocalDate.of(ride.getStartTime().getYear(), ride.getStartTime().getMonth(), ride.getStartTime().getDayOfMonth());
            if (!totalRides.containsKey(date)) {
                totalRides.put(date, 0);
                acceptedRides.put(date, 0);
            }
            totalRides.put(date, totalRides.get(date)+ 1);
            if (ride.getRideStatus() != Ride.RIDE_STATUS.rejected) acceptedRides.put(date, acceptedRides.get(date) + 1);
        }
        double total = 0;
        for (Map.Entry<LocalDate, Integer> set: totalRides.entrySet()) {
            total += 100 * acceptedRides.get(set.getKey())/(double) set.getValue();
        }
        return total/ (double) totalRides.size();
    }

    private double getAverageKmDay(ArrayList<Ride> rides) {
        double total = 0;
        HashMap<LocalDate, Double> values = new HashMap<>();
        for (Ride ride: rides) {
            Route route = ride.getRoutes().iterator().next();
            LocalDate date = LocalDate.of(ride.getStartTime().getYear(), ride.getStartTime().getMonth(), ride.getStartTime().getDayOfMonth());
            if (!values.containsKey(date)) values.put(date, 0.0);
            total += route.getMileage();
            values.put(date, values.get(date)+route.getMileage());
        }
        return total/ (double) values.size();
    }

    private double getAverageAcceptanceMonth(ArrayList<Ride> rides) {
        HashMap<LocalDate, Integer> totalRides = new HashMap<>();
        HashMap<LocalDate, Integer> acceptedRides = new HashMap<>();
        for (Ride ride: rides) {
            LocalDate date = LocalDate.of(ride.getStartTime().getYear(), ride.getStartTime().getMonth(), 1);
            if (!totalRides.containsKey(date)) {
                totalRides.put(date, 0);
                acceptedRides.put(date, 0);
            }
            totalRides.put(date, totalRides.get(date)+ 1);
            if (ride.getRideStatus() != Ride.RIDE_STATUS.rejected) acceptedRides.put(date, acceptedRides.get(date) + 1);
        }
        double total = 0;
        for (Map.Entry<LocalDate, Integer> set: totalRides.entrySet()) {
            total += 100 * acceptedRides.get(set.getKey())/(double) set.getValue();
        }
        return total/ (double) totalRides.size();
    }

    private double getAverageIncomeMonth(ArrayList<Ride> rides) {
        double total = 0;
        HashMap<LocalDate, Double> values = new HashMap<>();
        for (Ride ride: rides) {
            LocalDate date = LocalDate.of(ride.getStartTime().getYear(), ride.getStartTime().getMonth(), 1);
            if (!values.containsKey(date)) values.put(date, 0.0);
            total += ride.getPrice();
            values.put(date, values.get(date)+ ride.getPrice());
        }
        return total/ (double) values.size();
    }

    private double getAverageKmMonth(ArrayList<Ride> rides) {
        double total = 0;
        HashMap<LocalDate, Double> values = new HashMap<>();
        for (Ride ride: rides) {
            Route route = ride.getRoutes().iterator().next();
            LocalDate date = LocalDate.of(ride.getStartTime().getYear(), ride.getStartTime().getMonth(), 1);
            if (!values.containsKey(date)) values.put(date, 0.0);
            total += route.getMileage();
            values.put(date, values.get(date)+route.getMileage());
        }
        return total/ (double) values.size();
    }

    private double getAverageWorkingHourMonth(Driver driver) {
        ArrayList<WorkingTime> workingTimes = workingTimeRepository.findByDriver(driver);
        long total = 0;
        HashMap<LocalDate, Long> values = new HashMap<>();
        for (WorkingTime workingTime: workingTimes) {
            LocalDate date = LocalDate.of(workingTime.getStartTime().getYear(), workingTime.getStartTime().getMonth(), 1);
            if (!values.containsKey(date)) values.put(date, 0L);
            if (workingTime.getEndTime() == null){
                values.put(date, values.get(date) + ChronoUnit.MINUTES.between(workingTime.getStartTime(), LocalDateTime.now()));
                total += ChronoUnit.MINUTES.between(workingTime.getStartTime(), LocalDateTime.now());
            }
            else {
                total += ChronoUnit.MINUTES.between(workingTime.getStartTime(), workingTime.getEndTime());
                values.put(date, values.get(date) + ChronoUnit.MINUTES.between(workingTime.getStartTime(), workingTime.getEndTime()));
            }
        }
        return total/ (double) values.size();
    }

    private double getAverageWorkingHourDay(Driver driver) {
        ArrayList<WorkingTime> workingTimes = workingTimeRepository.findByDriver(driver);
        long total = 0;
        HashMap<LocalDate, Long> values = new HashMap<>();
        for (WorkingTime workingTime: workingTimes) {
            LocalDate date = LocalDate.of(workingTime.getStartTime().getYear(), workingTime.getStartTime().getMonth(), workingTime.getStartTime().getDayOfMonth());
            if (!values.containsKey(date)) values.put(date, 0L);
            if (workingTime.getEndTime() == null){
                values.put(date,values.get(date) + ChronoUnit.MINUTES.between(workingTime.getStartTime(), LocalDateTime.now()));
                total += ChronoUnit.MINUTES.between(workingTime.getStartTime(), LocalDateTime.now());
            }
            else {
                total += ChronoUnit.MINUTES.between(workingTime.getStartTime(), workingTime.getEndTime());
                values.put(date, values.get(date) + ChronoUnit.MINUTES.between(workingTime.getStartTime(), workingTime.getEndTime()));
            }
        }
        return total/ (double) values.size();
    }

    private double getAverageWorkingHourYear(Driver driver) {
        ArrayList<WorkingTime> workingTimes = workingTimeRepository.findByDriver(driver);
        long total = 0;
        HashMap<Integer, Long> values = new HashMap<>();
        for (WorkingTime workingTime: workingTimes) {
            Integer date = workingTime.getStartTime().getYear();
            if (!values.containsKey(date)) values.put(date, 0L);
            if (workingTime.getEndTime() == null){
                values.put(date, values.get(date) + ChronoUnit.MINUTES.between(workingTime.getStartTime(), LocalDateTime.now()));
                total += ChronoUnit.MINUTES.between(workingTime.getStartTime(), LocalDateTime.now());
            }
            else {
                total += ChronoUnit.MINUTES.between(workingTime.getStartTime(), workingTime.getEndTime());
                values.put(date, values.get(date) + ChronoUnit.MINUTES.between(workingTime.getStartTime(), workingTime.getEndTime()));
            }
        }
        return total/ (double) values.size();
    }
}
