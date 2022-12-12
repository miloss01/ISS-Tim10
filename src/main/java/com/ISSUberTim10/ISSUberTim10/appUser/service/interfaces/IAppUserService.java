package com.ISSUberTim10.ISSUberTim10.appUser.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface IAppUserService {
    public Collection<AppUser> getAll();

    public void createAll();

    public void deleteAll();

    Page<AppUser> getAll(Pageable page);
}
