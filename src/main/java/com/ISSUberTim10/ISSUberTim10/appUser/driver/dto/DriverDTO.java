package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    public DriverDTO(Driver driver) {
        this(driver.getId().intValue(), driver.getName(), driver.getLastName(), driver.getProfileImage(), driver.getPhone(), driver.getEmail(), driver.getAddress(), null);
    }

}
