package com.ISSUberTim10.ISSUberTim10.repository;

import com.ISSUberTim10.ISSUberTim10.domain.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface RideRepository extends JpaRepository<Ride, Long> {



    public Page<Ride> findAll(Pageable pageable);

}