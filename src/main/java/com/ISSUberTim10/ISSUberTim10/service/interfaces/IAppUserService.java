package com.ISSUberTim10.ISSUberTim10.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.domain.AppUser;

import java.util.Collection;

public interface IAppUserService {
    public Collection<AppUser> getAll();

    public void createAll();

    public void deleteAll();
}
