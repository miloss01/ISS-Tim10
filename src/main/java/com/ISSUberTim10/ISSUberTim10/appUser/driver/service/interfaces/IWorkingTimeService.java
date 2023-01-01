package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.WorkingTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IWorkingTimeService {

    public WorkingTime save(WorkingTime workingTime);
    public WorkingTime getById(Long id);
    public List<WorkingTime> getByDriver(Pageable pageable, Driver driver, String from, String to);

}
