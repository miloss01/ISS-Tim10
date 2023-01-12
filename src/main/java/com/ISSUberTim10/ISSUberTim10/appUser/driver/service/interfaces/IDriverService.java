package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces;


import com.ISSUberTim10.ISSUberTim10.appUser.driver.Document;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.*;


public interface IDriverService {

    public Driver setVehicle(Vehicle vehicle);
    public Driver saveDriver(Driver driver);
    public List<Driver> getAllDrivers(Pageable pageable);

    public Driver getById(Long id);
    public Optional<Driver> getByEmail(String email);


    public ResponseEntity<ChangeRequestDTO> updateChangeRequest(Integer driverId, ChangeRequestDTO requestDTO);

    public ResponseEntity<ChangeRequestResponseDTO> getChangeRequests();

    ResponseEntity<ChangeRequestDTO> approveChangeRequest(Integer driverId, ChangeRequestDTO requestDTO);

    ArrayList<Document> findDocumentsByDriverId(Long valueOf);

    Driver findDriverById(Integer id);
}
