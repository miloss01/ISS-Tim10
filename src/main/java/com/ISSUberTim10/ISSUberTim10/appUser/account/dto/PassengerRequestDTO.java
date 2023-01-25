package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
public class PassengerRequestDTO {

    @Size(max = 100, message = "Field (name) cannot be longer than 100 characters!")
    private String name;

    @Size(max = 100, message = "Field (surname) cannot be longer than 100 characters!")
    private String surname;

    @Pattern(regexp="^data:image.*$",message="Field (profilePicture) must be an image")
    private String profilePicture;

    @Size(max = 18, message = "Field (telephoneNumber) cannot be longer than 18 characters!")
    private String telephoneNumber;

    @Email(message = "Field (email) does not have valid format.")
    @Size(max = 100, message = "Field (email) cannot be longer than 100 characters!")
    private String email;

    @Size(max = 100, message = "Field (address) cannot be longer than 100 characters!")
    private String address;

    //    @JsonInclude(JsonInclude.Include. NON_NULL)
//    @Size(min = 3, message = "Field (password) is required!")
//    @NotBlank(message = "Field (password) is required!")
    private String password;
}
