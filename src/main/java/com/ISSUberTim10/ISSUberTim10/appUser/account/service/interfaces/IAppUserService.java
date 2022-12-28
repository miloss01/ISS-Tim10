package com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Optional;

public interface IAppUserService {
    public Collection<AppUser> getAll();

    UserExpandedDTO getById(Integer id);

    public void createAll();

    public void deleteAll();

    Page<AppUser> getAll(Pageable page);

    public ResponseEntity<PassengerResponseDTO> getPassenger(Integer id);
    public Optional<AppUser> findByEmail(String email);
    public Optional<AppUser> findById(Long id);

    public ResponseEntity<String> blockUser(@PathVariable Integer id);

    public ResponseEntity<String> unblockUser(@PathVariable Integer id);

    public ResponseEntity<IsBlockedDTO> isBlocked(@RequestParam Integer id);

    public ResponseEntity<NoteDTO> sendMessage(@PathVariable Integer id,
                                               @RequestBody NoteMessageDTO messageDTO);


    ResponseEntity<NoteResponseDTO> getNotes(@PathVariable Integer id, Pageable page);

    public AppUser save(AppUser appUser);
}
