package com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.Role;
import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Note;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.*;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IAppUserService;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.INoteService;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.AppUserRepository;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomExceptionWithMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService implements IAppUserService {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    private INoteService noteService;

    private static final Long ADMIN_ID = 5L;

    @Override
    public Collection<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    @Override
    public UserExpandedDTO getById(Integer id) {
        AppUser user = appUserRepository.getById(id.longValue());
        return new UserExpandedDTO(user);
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
    public List<AppUser> getAll(Pageable page) {
        return appUserRepository.findAll(page).getContent();
    }

    @Override
    public ResponseEntity<PassengerResponseDTO> getPassenger(Integer id) {
        Optional<AppUser> found = appUserRepository.findById(id.longValue());
        if (!found.isPresent()) {
            throw new CustomException("Passenger does not exist!", HttpStatus.NOT_FOUND);
        }
        AppUser appUser = found.get();
        if (appUser.getRole() != Role.PASSENGER) throw new CustomException("Passenger does not exist!", HttpStatus.NOT_FOUND);
        PassengerResponseDTO passengerResponse = new PassengerResponseDTO(appUser);
        return new ResponseEntity<>(passengerResponse, HttpStatus.OK);
    }

    @Override
    public AppUser findByEmail(String email) {
        Optional<AppUser> found = appUserRepository.findByEmail(email);
        if (!found.isPresent()) {
            throw new CustomException("User does not exist!", HttpStatus.NOT_FOUND);
        }
        return found.get();
    }

    @Override
    public AppUser findById(Long id) {
        Optional<AppUser> found = appUserRepository.findById(id);
        if (!found.isPresent()) {
            throw new CustomException("User does not exist!", HttpStatus.NOT_FOUND);
        }
        return found.get();
    }

    @Override
    public ResponseEntity<String> blockUser(Integer id) {
        Optional<AppUser> found = appUserRepository.findById(id.longValue());
        if (!found.isPresent()) {
            throw new CustomException("User does not exist!", HttpStatus.NOT_FOUND);
        } else {
            AppUser appUser = found.get();
            if (appUser.isBlockedFlag() == true)
                throw new CustomExceptionWithMessage("User already blocked!", HttpStatus.BAD_REQUEST);
            appUser.setBlockedFlag(true);
            appUserRepository.save(appUser);
        }
        return new ResponseEntity<String>("User successfully blocked", HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<String> unblockUser(Integer id) {
        Optional<AppUser> found = appUserRepository.findById(id.longValue());
        if (!found.isPresent()) {
            throw new CustomException("User does not exist!", HttpStatus.NOT_FOUND);
        } else {
            AppUser appUser = found.get();
            if (appUser.isBlockedFlag() == false)
                throw new CustomExceptionWithMessage("User is not blocked!", HttpStatus.BAD_REQUEST);
            appUser.setBlockedFlag(false);
            appUserRepository.save(appUser);
        }
        return new ResponseEntity<String>("User successfully unblocked", HttpStatus.NO_CONTENT);

    }

    @Override
    public ResponseEntity<IsBlockedDTO> isBlocked(Integer id) {
        Optional<AppUser> found = appUserRepository.findById(id.longValue());
        if (!found.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else {
            AppUser appUser = found.get();
            return new ResponseEntity<IsBlockedDTO>(
                    new IsBlockedDTO(appUser.getId().intValue(), appUser.isBlockedFlag()), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<NoteDTO> sendMessage(Integer id, NoteMessageDTO messageDTO) {

        Optional<AppUser> appUser = appUserRepository.findById(id.longValue());

        if (!appUser.isPresent())
            throw new CustomException("User does not exist!", HttpStatus.NOT_FOUND);

        Note note = new Note();
        note.setMessage(messageDTO.getMessage());
        note.setNoteDate(new Date());
        note.setAppUser(appUser.get());
        NoteDTO noteDTO = new NoteDTO(noteService.save(note));
        return new ResponseEntity<>(noteDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NoteResponseDTO> getNotes(Integer id, Pageable page) {
        return noteService.getAll(id);
    }



    @Override
    public AppUser save(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public Optional<AppUser> findByEmailOpt(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public AppUser getAdmin() {
        return appUserRepository.getById(ADMIN_ID);
    }


}
