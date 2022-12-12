package com.ISSUberTim10.ISSUberTim10.appUser.repository;

import com.ISSUberTim10.ISSUberTim10.appUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}