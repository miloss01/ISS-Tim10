package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.WorkingTime;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.WorkingTimeRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IWorkingTimeService;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkingTimeService implements IWorkingTimeService {

    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @Override
    public WorkingTime update(WorkingTime workingTime) {
        Optional<WorkingTime> found = workingTimeRepository.findFirstByDriverOrderByStartTimeStartTimeDesc(workingTime.getDriver());
        if(!found.isPresent()) throw new CustomException("Working time does not exist!", HttpStatus.NOT_FOUND);
        checkIfVehicleRegistered(workingTime);
        checkIfShiftNotOngoing(workingTime);
        WorkingTime workingTim = found.get();
        workingTim.setEndTime(workingTime.getEndTime());
        return workingTimeRepository.save(workingTim);
    }

    @Override
    public WorkingTime saveUpdated(WorkingTime workingTime) {
        checkIfVehicleRegistered(workingTime);
        checkIfShiftNotOngoing(workingTime);
        return workingTimeRepository.save(workingTime);
    }

    private void checkIfShiftNotOngoing(WorkingTime workingTime) {
        if (!workingTime.getDriver().isActiveFlag()){
            throw new CustomException("No shift is ongoing!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public WorkingTime save(WorkingTime workingTime) {
        checkWorkingHours(workingTime);
        checkIfVehicleRegistered(workingTime);
        checkIfShiftOngoing(workingTime);
        return workingTimeRepository.save(workingTime);
    }

    private void checkIfShiftOngoing(WorkingTime workingTime) {
        if (workingTime.getDriver().isActiveFlag()){
            throw new CustomException("Shift already ongoing!", HttpStatus.BAD_REQUEST);
        }
    }

    private static void checkIfVehicleRegistered(WorkingTime workingTime) {
        try {
            workingTime.getDriver().getVehicle().getVehicleType();
        }catch (NullPointerException ex) {
            throw new CustomException("Cannot start shift because the vehicle is not defined!", HttpStatus.BAD_REQUEST);
        }
    }

    private void checkWorkingHours(WorkingTime workingTime) {
        long totalMinutes = 0;
        ArrayList<WorkingTime> workingTimes = workingTimeRepository.findByDriverAndStartTimeGreaterThanEqual(workingTime.getDriver(), LocalDateTime.now().minusDays(1));
        for (WorkingTime pastTime:workingTimes) {
            totalMinutes += ChronoUnit.MINUTES.between(pastTime.getStartTime(), pastTime.getEndTime());
        }
        if (totalMinutes > 8*60) {
            throw new CustomException("Cannot start shift because you exceeded the 8 hours limit in last 24 hours!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public WorkingTime getById(Long id) {

        Optional<WorkingTime> workingTime = workingTimeRepository.findById(id);

        if (!workingTime.isPresent())
            throw new CustomException("Working time does not exist!", HttpStatus.NOT_FOUND);

        return workingTime.get();

    }

    @Override
    public List<WorkingTime> getByDriver(Pageable pageable, Driver driver, String from, String to) {

        LocalDateTime fromDate;
        LocalDateTime toDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (from == null)
            fromDate = LocalDateTime.of(2000, 1, 1, 1, 1);
        else
            fromDate = LocalDateTime.parse(from, formatter);

        if (to == null)
            toDate = LocalDateTime.of(3000, 1, 1, 1, 1);
        else
            toDate = LocalDateTime.parse(to, formatter);

        Page<WorkingTime> page = workingTimeRepository.findByDriver(pageable, driver);

        List<WorkingTime> workingTimes = new ArrayList<>();

        for (WorkingTime workingTime : page.getContent())
            if (workingTime.getStartTime() != null &&
                workingTime.getEndTime() != null &&
                workingTime.getStartTime().isAfter(fromDate) &&
                workingTime.getEndTime().isBefore(toDate))
                workingTimes.add(workingTime);

        return workingTimes;

    }
}
