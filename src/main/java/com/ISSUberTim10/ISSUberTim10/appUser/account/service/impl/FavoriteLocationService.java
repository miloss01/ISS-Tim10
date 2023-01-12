package com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl;

import com.ISSUberTim10.ISSUberTim10.ride.FavoriteLocation;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.FavoriteLocationRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IFavoriteLocationService;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteLocationService implements IFavoriteLocationService {

    @Autowired
    FavoriteLocationRepository favoriteLocationRepository;



    @Override
    public FavoriteLocation save(FavoriteLocation location, long makerId) {

        if (getByMaker(makerId).size() >= 10) {
            throw new CustomException("Number of favorite rides cannot exceed 10!", HttpStatus.BAD_REQUEST);
        }

        return favoriteLocationRepository.save(location);
    }

    @Override
    public List<FavoriteLocation> getAll() {
        return favoriteLocationRepository.findAll();
    }

    @Override
    public FavoriteLocation getById(Long id) {
        Optional<FavoriteLocation> location = favoriteLocationRepository.findById(id);
        if (!location.isPresent())
            throw new CustomException("Favorite location does not exist!", HttpStatus.NOT_FOUND);

        return location.get();
    }

    @Override
    public List<FavoriteLocation> getByMaker(long makerId) {
        return favoriteLocationRepository.findByMakerId(makerId);
    }

    @Override
    public void delete(long favoriteId) {
        favoriteLocationRepository.deleteById(favoriteId);
    }
}
