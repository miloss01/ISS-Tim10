package com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.PassengerRequestDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.PassengerResponseDTO;
import org.springframework.http.ResponseEntity;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IPassengerService {
    public ResponseEntity<PassengerResponseDTO> getPassenger(Integer id);
    public Passenger getPassenger(Long id);
    public ResponseEntity<PassengerResponseDTO> updatePassenger(Integer id, PassengerRequestDTO passengerRequestDTO);
    public ResponseEntity<PassengerResponseDTO> savePassenger(PassengerRequestDTO passengerRequestDTO);
    public Passenger savePassenger(Passenger passenger);
    public List<Passenger> getAllPassengers(Pageable pageable);
}
