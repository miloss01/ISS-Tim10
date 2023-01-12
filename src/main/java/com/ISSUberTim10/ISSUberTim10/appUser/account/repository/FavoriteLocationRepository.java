package com.ISSUberTim10.ISSUberTim10.appUser.account.repository;

import com.ISSUberTim10.ISSUberTim10.ride.FavoriteLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteLocationRepository extends JpaRepository<FavoriteLocation, Long> {

    public List<FavoriteLocation> findByMakerId(long makerId);

}
