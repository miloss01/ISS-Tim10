package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

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

}
