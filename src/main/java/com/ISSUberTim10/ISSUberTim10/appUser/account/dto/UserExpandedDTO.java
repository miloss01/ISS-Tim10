package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserExpandedDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
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

    public UserExpandedDTO(AppUser user) {
        this(user.getId(), user.getName(), user.getLastName(), user.getProfileImage(),
                user.getPhone(), user.getEmail(), user.getAddress());
    }
}
