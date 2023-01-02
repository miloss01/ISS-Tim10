package com.ISSUberTim10.ISSUberTim10.appUser.account.controller;

import com.ISSUberTim10.ISSUberTim10.ride.dto.EstimatedDataRequestDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.EstimatedDataResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "api/unregisteredUser")
public class UnregisteredUserController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstimatedDataResponseDTO> getEstimatedData(@RequestBody EstimatedDataRequestDTO estimatedDataRequestDTO) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(estimatedDataRequestDTO);
        return new ResponseEntity<>(
                new EstimatedDataResponseDTO(10, 450),
                HttpStatus.OK
        );
    }

}
