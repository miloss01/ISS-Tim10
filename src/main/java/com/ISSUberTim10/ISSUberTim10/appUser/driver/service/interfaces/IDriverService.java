package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.*;
import com.ISSUberTim10.ISSUberTim10.ride.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IDriverService {
    public ResponseEntity<VehicleDTO> getVehicle(Integer id);

    public ResponseEntity<VehicleDTO> saveVehicle(Integer id, VehicleDTO vehicleDTO) ;

    public ResponseEntity<VehicleDTO> updateVehicle( Integer id, VehicleDTO vehicleDTO);

    public ResponseEntity<DriverDTO> saveDriver( DriverDTO driverDTO);

    public ResponseEntity<DriversDTO> getDrivers(Pageable page);

    public ResponseEntity<DriverDTO> getDriver(Integer id);

    public ResponseEntity<DriverDTO> updateDriver(Integer id, DriverDTO driverDTO);

    public ResponseEntity<List<DocumentDTO>> getDocuments( Integer id);

    public ResponseEntity<Void> deleteDocument( Integer documentId);

    public ResponseEntity<DocumentDTO> updateDocument(Integer id, DocumentDTO documentDTO);

    public ResponseEntity<WorkingHoursDTO> getWorkingHours(Integer id,
                                                           Pageable page,
                                                           String from,
                                                           String to) ;

    public ResponseEntity<WorkingHourDTO> saveWorkingHour(Integer id,
                                                          WorkingHourDTO workingHourDTO);

    public ResponseEntity<RideResponseDTO> getDriversRide( Integer id,
                                                          Pageable page,
                                                          String from,
                                                          String to) ;

    public ResponseEntity<WorkingHourDTO> getWorkingHour(Integer workingHourId);

    public ResponseEntity<WorkingHourDTO> updateWorkingHour(Integer workingHourId, WorkingHourDTO workingHourDTO);
}
