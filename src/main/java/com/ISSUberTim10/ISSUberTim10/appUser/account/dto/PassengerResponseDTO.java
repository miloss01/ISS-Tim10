package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PassengerResponseDTO {

    private Integer id;

    private String name;

    private String surname;

    private String profilePicture;

    private String telephoneNumber;

    private String email;

    private String address;

    public PassengerResponseDTO(AppUser passenger) {
        this((int) (long) passenger.getId(), passenger.getName(), passenger.getLastName(), passenger.getProfileImage(),
                passenger.getPhone(), passenger.getEmail(), passenger.getAddress());
    }

}
