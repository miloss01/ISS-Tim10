package com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.ride.FavoriteLocation;

import java.util.List;

public interface IFavoriteLocationService {

    public FavoriteLocation save(FavoriteLocation favoriteLocation, long makerId);

    public List<FavoriteLocation> getAll();

    public FavoriteLocation getById(Long id);

    public List<FavoriteLocation> getByMaker(long makerId);

    public void delete(long favoriteId);

}
