package com.ISSUberTim10.ISSUberTim10.repository;

import com.ISSUberTim10.ISSUberTim10.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}