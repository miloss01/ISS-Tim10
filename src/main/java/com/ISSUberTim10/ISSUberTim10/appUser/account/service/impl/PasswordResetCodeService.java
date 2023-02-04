package com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.PasswordResetCode;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.PasswordResetCodeRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IPasswordResetCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetCodeService implements IPasswordResetCodeService {

    @Autowired
    private PasswordResetCodeRepository passwordResetCodeRepository;

    @Override
    public Optional<PasswordResetCode> findByEmail(String email) {
        return passwordResetCodeRepository.findByEmail(email);
    }

    @Override
    public PasswordResetCode save(PasswordResetCode passwordResetCode) {
        return passwordResetCodeRepository.save(passwordResetCode);
    }

    @Override
    public void deleteById(Long id) {
        passwordResetCodeRepository.deleteById(id);
    }

}
