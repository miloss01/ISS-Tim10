package com.ISSUberTim10.ISSUberTim10.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.domain.AppUser;
import com.ISSUberTim10.ISSUberTim10.domain.Driver;
import com.ISSUberTim10.ISSUberTim10.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AppUserService implements IAppUserService {

    @Autowired
    AppUserRepository appUserRepository;
    @Override
    public Collection<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    @Override
    public void createAll() {
        Driver d1 = new Driver();
        //d1.setId(1L);
        d1.setName("d3");
        appUserRepository.save(d1);

        Driver d2 = new Driver();
        //d2.setId(2L);
        d2.setName("d4");
        appUserRepository.save(d2);
    }

    @Override
    public void deleteAll() {
        appUserRepository.deleteAll();
    }
}
