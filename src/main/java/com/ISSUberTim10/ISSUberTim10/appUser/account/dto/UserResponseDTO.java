package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class UserResponseDTO {

    @NotNull(message = "Field (id) is required!")
    private long id;

    @Email(message = "Field (email) does not have valid format.")
    @Size(max = 100, message = "Field (email) cannot be longer than 100 characters!")
    private String email;

    public UserResponseDTO(AppUser user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }
}
