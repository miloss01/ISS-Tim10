package com.ISSUberTim10.ISSUberTim10.appUser.account.repository;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

}