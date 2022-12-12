package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class PassengerRequestDTO {

    private String name;

    private String surname;

    private String profilePicture;

    private String telephoneNumber;

    private String email;

    private String address;

    private String password;
}
