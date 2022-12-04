package com.ISSUberTim10.ISSUberTim10.dto;

import com.ISSUberTim10.ISSUberTim10.domain.Passenger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PassengerResponseDTO {

    private long id;

    private String name;

    private String lastName;

    private String profileImage;

    private String phone;

    private String email;

    private String address;

    public PassengerResponseDTO(Passenger passenger) {
        this(passenger.getId(), passenger.getName(), passenger.getLastName(), passenger.getProfileImage(),
                passenger.getPhone(), passenger.getEmail(), passenger.getAddress());
    }

}
