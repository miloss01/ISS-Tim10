package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
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
public class UserForDisplayDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String name;
    private String surname;
    private String profilePicture;
    private String email;

    public UserForDisplayDTO(AppUser appUser) {
        this.id = appUser.getId();
        this.name = appUser.getName();
        this.surname = appUser.getLastName();
        this.profilePicture = appUser.getProfileImage();
        this.email = appUser.getEmail();
    }
}
