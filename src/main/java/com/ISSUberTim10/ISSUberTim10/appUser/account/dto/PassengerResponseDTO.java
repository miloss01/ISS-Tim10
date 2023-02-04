package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.account.UserActivation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PassengerResponseDTO {

    private Integer id;

    @Size(max = 100, message = "Field (name) cannot be longer than 100 characters!")
    private String name;

    @Size(max = 100, message = "Field (surname) cannot be longer than 100 characters!")
    private String surname;

    private String profilePicture;

    @Size(max = 18, message = "Field (telephoneNumber) cannot be longer than 18 characters!")
    private String telephoneNumber;

    @Email(message = "Field (email) does not have valid format.")
    @Size(max = 100, message = "Field (email) cannot be longer than 100 characters!")
    private String email;

    @Size(max = 100, message = "Field (address) cannot be longer than 100 characters!")
    private String address;

    public PassengerResponseDTO(AppUser passenger) {
        this((int) (long) passenger.getId(), passenger.getName(), passenger.getLastName(), passenger.getProfileImage(),
                passenger.getPhone(), passenger.getEmail(), passenger.getAddress());
    }

    public PassengerResponseDTO(UserActivation userActivation) {
        this(null, userActivation.getName(), userActivation.getLastName(), userActivation.getProfileImage(), userActivation.getPhone(), userActivation.getEmail(), userActivation.getAddress());
    }

}
