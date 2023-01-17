package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    @NotNull
    @Size(max = 100, message = "Field (name) cannot be longer than 100 characters!")
    private String name;
    @NotNull
    @Size(max = 100, message = "Field (surname) cannot be longer than 100 characters!")
    private String surname;
    @Pattern(regexp="^data:image.*$",message="Field (profilePicture) must be an image")
    private String profilePicture;
    @NotNull
    @Size(max = 18, message = "Field (telephoneNumber) cannot be longer than 18 characters!")
    private String telephoneNumber;
    @NotNull
    @Email(message = "Field (email) does not have valid format.")
    @Size(max = 100, message = "Field (email) cannot be longer than 100 characters!")
    private String email;
    @NotNull
    @Size(max = 100, message = "Field (address) cannot be longer than 100 characters!")
    private String address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    public DriverDTO(Driver driver) {
        this(driver.getId().intValue(), driver.getName(), driver.getLastName(), driver.getProfileImage(), driver.getPhone(), driver.getEmail(), driver.getAddress(), null);
    }

}
