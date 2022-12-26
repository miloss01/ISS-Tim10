package com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.PassengerResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

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

    @Override
    public Page<AppUser> getAll(Pageable page) {
        return appUserRepository.findAll(page);
    }

    @Override
    public ResponseEntity<PassengerResponseDTO> getPassenger(Integer id) {
        AppUser passenger = appUserRepository.getById(id.longValue());
        PassengerResponseDTO passengerResponse = new PassengerResponseDTO(passenger);
        return new ResponseEntity<>(passengerResponse, HttpStatus.OK);
    }

    @Override
    public Optional<AppUser> findByEmail(String email) { return appUserRepository.findByEmail(email); }

    @Override
    public Optional<AppUser> findById(Long id) {
        return appUserRepository.findById(id);
    }

    @Override
    public ResponseEntity<String> blockUser(Integer id) {
        Optional<AppUser> found = appUserRepository.findById(id.longValue());
        if (!found.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found to be blocked.");
        } else {
            AppUser appUser = found.get();
            appUser.setBlockedFlag(true);
            appUserRepository.save(appUser);
        }
        return new ResponseEntity<String>("User successfully blocked", HttpStatus.NO_CONTENT);
    }
    
}
