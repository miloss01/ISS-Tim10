package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class PassengerRequestDTO {

    private String name;

    private String lastName;

    private String profileImage;

    private String phone;

    private String email;

    private String address;

    private String password;
}
