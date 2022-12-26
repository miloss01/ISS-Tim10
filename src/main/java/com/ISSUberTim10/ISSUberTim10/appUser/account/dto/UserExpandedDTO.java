package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserExpandedDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;

    public UserExpandedDTO(AppUser user) {
        this(user.getId(), user.getName(), user.getLastName(), user.getProfileImage(),
                user.getPhone(), user.getEmail(), user.getAddress());
    }
}
