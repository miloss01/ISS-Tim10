package com.ISSUberTim10.ISSUberTim10.appUser.account.repository;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
