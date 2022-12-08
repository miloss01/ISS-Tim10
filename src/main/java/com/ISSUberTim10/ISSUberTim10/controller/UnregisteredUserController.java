package com.ISSUberTim10.ISSUberTim10.controller;

import com.ISSUberTim10.ISSUberTim10.dto.EstimatedDataRequestDTO;
import com.ISSUberTim10.ISSUberTim10.dto.EstimatedDataResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/unregisteredUser")
public class UnregisteredUserController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstimatedDataResponseDTO> getEstimatedData(@RequestBody EstimatedDataRequestDTO estimatedDataRequestDTO) {
        return new ResponseEntity<>(
                new EstimatedDataResponseDTO(10, 450),
                HttpStatus.OK
        );
    }

}
