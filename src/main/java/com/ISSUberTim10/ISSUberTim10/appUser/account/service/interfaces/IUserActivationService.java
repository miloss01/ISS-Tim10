package com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.UserActivation;

import javax.jws.soap.SOAPBinding;
import java.util.Optional;

public interface IUserActivationService {

    public Optional<UserActivation> findById(Long id);
    public UserActivation save(UserActivation userActivation);

    public void deleteById(Long id);

}
