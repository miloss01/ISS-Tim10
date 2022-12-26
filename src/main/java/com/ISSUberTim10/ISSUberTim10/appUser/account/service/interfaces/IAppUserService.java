package com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.PassengerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Optional;

public interface IAppUserService {
    public Collection<AppUser> getAll();

    public void createAll();

    public void deleteAll();

    Page<AppUser> getAll(Pageable page);

    public ResponseEntity<PassengerResponseDTO> getPassenger(Integer id);
    public Optional<AppUser> findByEmail(String email);
    public Optional<AppUser> findById(Long id);

    public ResponseEntity<String> blockUser(@PathVariable Integer id);

}
