package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PasswordResetCodeDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String newPassword;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;

}
