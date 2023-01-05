package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.impl;


import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.DriverRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IDriverService;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.ChangeRequest;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Document;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.*;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.ChangeRequestRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.DocumentRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.VehicleRepository;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
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
    public Driver getById(Long id) {
        Optional<Driver> driver = driverRepository.findById(id);

        if (!driver.isPresent())
            throw new CustomException("Driver does not exist!", HttpStatus.NOT_FOUND);

        return driver.get();
    }

    @Override
    public Optional<Driver> getByEmail(String email) {
        return driverRepository.findByEmail(email);
    }

    @Override
    public Driver setVehicle(Vehicle vehicle) {
        Optional<Driver> driver = driverRepository.findById(vehicle.getDriver().getId());

        if (!driver.isPresent())
            throw new CustomException("Driver does not exits!", HttpStatus.NOT_FOUND);

        driver.get().setVehicle(vehicle);
        return driverRepository.save(driver.get());
    }

    @Override
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public List<Driver> getAllDrivers(Pageable pageable) {
        Page<Driver> page = driverRepository.findAll(pageable);
        return page.getContent();
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

    @Override
    public ResponseEntity<ChangeRequestResponseDTO> getChangeRequests() {
        ArrayList<ChangeRequestDTO> changeRequestDTOS = new ArrayList<>();
        List<ChangeRequest> changeRequests = changeRequestRepository.findAll();
        for (ChangeRequest changeRequest: changeRequests) {
            if(!changeRequest.isApproved()){changeRequestDTOS.add(new ChangeRequestDTO(changeRequest));}
        }
        return new ResponseEntity<>(new ChangeRequestResponseDTO(changeRequestDTOS.size(), changeRequestDTOS), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ChangeRequestDTO> approveChangeRequest(Integer driverId, ChangeRequestDTO requestDTO) {
        Optional<Driver> foundDriver = driverRepository.findById(Long.valueOf(driverId));
        if (!foundDriver.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found to be blocked.");
        }
        Optional<ChangeRequest> found = changeRequestRepository.findByDriverId(Long.valueOf(driverId));
        if (!found.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found to be blocked.");
        }
        Driver driver = updateDriverWithNewInfo(requestDTO, foundDriver.get());
        ChangeRequest changeRequest = found.get();
        changeRequest.setApproved(true);
        changeRequestRepository.save(changeRequest);
        driverRepository.save(driver);
        return new ResponseEntity<>(requestDTO, HttpStatus.OK);
    }

    @Override
    public ArrayList<Document> findDocumentsByDriverId(Long valueOf) {
        return documentRepository.findByDriverId(valueOf);
    }

    @Override
    public Driver findDriverById(Integer id) {
        Optional<Driver> found = driverRepository.findById(id.longValue());
        if (!found.isPresent()){
            throw new CustomException("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        return found.get();
    }

    private Driver updateDriverWithNewInfo(ChangeRequestDTO requestDTO, Driver driver) {
        driver.setName(requestDTO.getUserDTO().getName());
        driver.setLastName(requestDTO.getUserDTO().getSurname());
        driver.setProfileImage(requestDTO.getUserDTO().getProfilePicture());
        driver.setPhone(requestDTO.getUserDTO().getTelephoneNumber());
        driver.setEmail(requestDTO.getUserDTO().getEmail());
        driver.setAddress(requestDTO.getUserDTO().getAddress());
        driver.getVehicle().setBabyFlag(requestDTO.getVehicleDTO().getBabyTransport());
        driver.getVehicle().setModel(requestDTO.getVehicleDTO().getModel());
        //driver.getVehicle().setVehicleType(requestDTO.getVehicleDTO().getVehicleType());
        driver.getVehicle().setNumOfSeats(requestDTO.getVehicleDTO().getPassengerSeats());
        driver.getVehicle().setPetsFlag(requestDTO.getVehicleDTO().getPetTransport());
        driver.getVehicle().setRegistrationPlate(requestDTO.getVehicleDTO().getLicenseNumber());
        return driver;
    }

}
