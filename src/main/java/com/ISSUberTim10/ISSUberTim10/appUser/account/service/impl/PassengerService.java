package com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.PassengerRequestDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.PassengerResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.AppUserRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.PassengerRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IPassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PassengerService implements IPassengerService {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Override //moze se obrisati, stoji u AppUserService jer treba i za passengera i za drivera
    public ResponseEntity<PassengerResponseDTO> getPassenger(Integer id) {
        AppUser passenger = appUserRepository.getById(id.longValue());
        PassengerResponseDTO passengerResponse = new PassengerResponseDTO(passenger);
        return new ResponseEntity<>(passengerResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PassengerResponseDTO> updatePassenger(Integer id, PassengerRequestDTO passengerRequestDTO) {
        Passenger passenger = passengerRepository.getById(id.longValue());
        passenger.setName(passengerRequestDTO.getName());
        passenger.setLastName(passengerRequestDTO.getSurname());
        passenger.setProfileImage(passengerRequestDTO.getProfilePicture());
        passenger.setPhone(passengerRequestDTO.getTelephoneNumber());
        passenger.setEmail(passengerRequestDTO.getEmail());
        passenger.setAddress(passengerRequestDTO.getAddress());
        passengerRepository.save(passenger);
        return new ResponseEntity<>(new PassengerResponseDTO(passenger), HttpStatus.OK);
    }
}