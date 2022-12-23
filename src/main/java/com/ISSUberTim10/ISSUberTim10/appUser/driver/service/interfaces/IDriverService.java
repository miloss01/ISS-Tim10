package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.VehicleDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import org.springframework.http.ResponseEntity;

public interface IDriverService {
    public ResponseEntity<VehicleDTO> getVehicle(Integer id);

    public ResponseEntity<VehicleDTO> saveVehicle(Integer id, VehicleDTO vehicleDTO) ;

    public ResponseEntity<VehicleDTO> updateVehicle( Integer id, VehicleDTO vehicleDTO);

}
