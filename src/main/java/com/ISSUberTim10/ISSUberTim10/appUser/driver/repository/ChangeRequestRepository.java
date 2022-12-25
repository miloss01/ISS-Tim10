package com.ISSUberTim10.ISSUberTim10.appUser.driver.repository;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.ChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChangeRequestRepository extends JpaRepository<ChangeRequest, Long> {
    Optional<ChangeRequest> findByDriverId(Long valueOf);
}
