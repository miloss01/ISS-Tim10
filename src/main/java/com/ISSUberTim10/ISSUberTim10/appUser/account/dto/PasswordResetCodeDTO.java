package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PasswordResetCodeDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Email(message = "Field (email) does not have valid format.")
    @Size(max = 100, message = "Field (email) cannot be longer than 100 characters!")
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String newPassword;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;

}
