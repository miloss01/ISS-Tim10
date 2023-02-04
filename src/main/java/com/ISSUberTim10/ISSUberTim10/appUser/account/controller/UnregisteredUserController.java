package com.ISSUberTim10.ISSUberTim10.appUser.account.controller;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.VehicleType;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IVehicleTypeService;
import com.ISSUberTim10.ISSUberTim10.ride.dto.EstimatedDataRequestDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.EstimatedDataResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(value = "api/unregisteredUser")
@Validated
public class UnregisteredUserController {

    @Autowired
    private IVehicleTypeService vehicleTypeService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstimatedDataResponseDTO> getEstimatedData(@Valid @RequestBody EstimatedDataRequestDTO estimatedDataRequestDTO) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(estimatedDataRequestDTO);

        VehicleType vehicleType = vehicleTypeService.getByName(Vehicle.VEHICLE_TYPE.standard);
        System.out.println(vehicleType.getPrice());

        Double vehicle_price = vehicleType.getPrice();

        if (estimatedDataRequestDTO.getVehicleType() != null)
            vehicle_price = vehicleTypeService.getByName(Vehicle.VEHICLE_TYPE.valueOf(estimatedDataRequestDTO.getVehicleType().toLowerCase())).getPrice();

        return new ResponseEntity<>(
                new EstimatedDataResponseDTO(10, (int) (vehicle_price + estimatedDataRequestDTO.getDistance().intValue() * 120)),
                HttpStatus.OK
        );
    }

}
