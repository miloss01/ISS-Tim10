package com.ISSUberTim10.ISSUberTim10.auth;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("userSecurity")
public class UserSecurity {

    @Autowired
    private IAppUserService appUserService;

    public boolean hasUserId(Authentication authentication, Long userId, String entity) {

        String email = authentication.getName();

        Optional<AppUser> appUser = appUserService.findByEmail(email);

        if (!appUser.isPresent())
            throw new CustomException("User does not exist!", HttpStatus.NOT_FOUND);

        if (appUser.get().getId() != userId)
            throw new CustomException(entity + " doesn't exist!", HttpStatus.NOT_FOUND);

        return true;

    }

}
