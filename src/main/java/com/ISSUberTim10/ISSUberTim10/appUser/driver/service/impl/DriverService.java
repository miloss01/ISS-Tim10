package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.ChangeRequest;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Document;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.*;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.ChangeRequestRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.DocumentRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.DriverRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.VehicleRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IDriverService;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Service
public class DriverService implements IDriverService {
    @Autowired
    ChangeRequestRepository changeRequestRepository;
    @Autowired
    DriverRepository driverRepository;
    @Autowired
    DocumentRepository documentRepository;

    @Override
    public ResponseEntity<VehicleDTO> getVehicle(Integer id) {
        Optional<Driver> found = driverRepository.findById(Long.valueOf(id));
        if (!found.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found to be blocked.");
        }
        Vehicle vehicle = found.get().getVehicle();
        VehicleDTO vehicleDTO = new VehicleDTO(vehicle);
        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VehicleDTO> saveVehicle(Integer id, VehicleDTO vehicleDTO) {
        return null;
    }

    @Override
    public ResponseEntity<VehicleDTO> updateVehicle(Integer id, VehicleDTO vehicleDTO) {
        return null;
    }

    @Override
    public ResponseEntity<DriverDTO> saveDriver(DriverDTO driverDTO) {
        return null;
    }

    @Override
    public ResponseEntity<DriversDTO> getDrivers(Pageable page) {
        return null;
    }

    @Override
    public ResponseEntity<DriverDTO> getDriver(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<DriverDTO> updateDriver(Integer id, DriverDTO driverDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<DocumentDTO>> getDocuments(Integer id) {
        ArrayList<DocumentDTO> documentDTOS = new ArrayList<>();
        ArrayList<Document> documents = documentRepository.findByDriverId(Long.valueOf(id));
        for (Document document: documents) {
            documentDTOS.add(new DocumentDTO(document));
        }
        return new ResponseEntity<>(documentDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteDocument(Integer documentId) {
        return null;
    }

    @Override
    public ResponseEntity<DocumentDTO> updateDocument(Integer id, DocumentDTO documentDTO) {
        return null;
    }

    @Override
    public ResponseEntity<WorkingHoursDTO> getWorkingHours(Integer id, Pageable page, String from, String to) {
        return null;
    }

    @Override
    public ResponseEntity<WorkingHourDTO> saveWorkingHour(Integer id, WorkingHourDTO workingHourDTO) {
        return null;
    }

    @Override
    public ResponseEntity<RideResponseDTO> getDriversRide(Integer id, Pageable page, String from, String to) {
        return null;
    }

    @Override
    public ResponseEntity<WorkingHourDTO> getWorkingHour(Integer workingHourId) {
        return null;
    }

    @Override
    public ResponseEntity<WorkingHourDTO> updateWorkingHour(Integer workingHourId, WorkingHourDTO workingHourDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ChangeRequestDTO> updateChangeRequest(Integer driverId, ChangeRequestDTO requestDTO) {
        Optional<Driver> foundDriver = driverRepository.findById(Long.valueOf(driverId));
        if (!foundDriver.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found to be blocked.");
        }
        Driver driver = foundDriver.get();

        ChangeRequest newChangeRequest = new ChangeRequest(requestDTO, driver);
        Optional<ChangeRequest> found = changeRequestRepository.findByDriverId(Long.valueOf(driverId));
        if (found.isPresent()) {
            ChangeRequest changeRequest = found.get();
            newChangeRequest.setId(changeRequest.getId());
            //newChangeRequest.setApproved(false);
        }
        changeRequestRepository.save(newChangeRequest);
        return new ResponseEntity<>(requestDTO, HttpStatus.OK);
    }
}
