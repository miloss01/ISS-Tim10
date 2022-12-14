package com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.PassengerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface IAppUserService {
    public Collection<AppUser> getAll();

    public void createAll();

    public void deleteAll();

    Page<AppUser> getAll(Pageable page);

    public ResponseEntity<PassengerResponseDTO> getPassenger(Integer id);
}
