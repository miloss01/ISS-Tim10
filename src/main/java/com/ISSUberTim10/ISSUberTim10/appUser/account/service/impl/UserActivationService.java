package com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.UserActivation;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.UserActivationRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IUserActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserActivationService implements IUserActivationService {

    @Autowired
    UserActivationRepository userActivationRepository;

    @Override
    public Optional<UserActivation> findById(Long id) {
        return userActivationRepository.findById(id);
    }

    @Override
    public UserActivation save(UserActivation userActivation) {
        return userActivationRepository.save(userActivation);
    }

    @Override
    public void deleteById(Long id) {
        userActivationRepository.deleteById(id);
    }
}
