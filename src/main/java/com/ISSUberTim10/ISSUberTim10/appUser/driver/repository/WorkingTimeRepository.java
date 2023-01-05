package com.ISSUberTim10.ISSUberTim10.appUser.driver.repository;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.WorkingTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Long> {

    Page<WorkingTime> findByDriver(Pageable pageable, Driver driver);

    ArrayList<WorkingTime> findByDriverAndStartTimeGreaterThanEqual(Driver driver, LocalDateTime minusDays);

    @Query(value="select * from WORKING_TIME order by start_time desc limit 1", nativeQuery = true)
    Optional<WorkingTime> findFirstByDriverOrderByStartTimeStartTimeDesc(Driver driver);
}
