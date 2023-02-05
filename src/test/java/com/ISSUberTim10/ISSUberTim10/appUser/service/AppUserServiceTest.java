package com.ISSUberTim10.ISSUberTim10.appUser.service;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.AppUserRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl.AppUserService;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Autowired
    @InjectMocks
    private AppUserService appUserService;

    @Test
    @DisplayName("Should Throw ResponseStatusException Exception if AppUser with ID doesn't exist")
    public void shouldNotBlockUserThatDoesntExist() {

        Mockito.when(appUserRepository.findById(123L)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> appUserService.blockUser(123));

        verify(appUserRepository, times(1)).findById(123L);
    }

    @Test
    @DisplayName("AppUser blocked flag becomes true")
    public void blocksUserWithExistingId() {

        AppUser appUser = new Passenger();
        appUser.setId(1L);
        Mockito.when(appUserRepository.findById(1L)).thenReturn(Optional.of(appUser));

        appUserService.blockUser(appUser.getId().intValue());

        assertTrue(appUser.isBlockedFlag());

        verify(appUserRepository, times(1)).findById(appUser.getId());


    }


}
