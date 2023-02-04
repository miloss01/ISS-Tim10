package com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.PasswordResetCode;

import java.util.Optional;

public interface IPasswordResetCodeService {

    public Optional<PasswordResetCode> findByEmail(String email);
    public PasswordResetCode save(PasswordResetCode passwordResetCode);
    public void deleteById(Long id);

}
