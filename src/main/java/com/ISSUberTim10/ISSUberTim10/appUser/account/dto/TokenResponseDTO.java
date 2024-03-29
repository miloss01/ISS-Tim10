package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDTO {
    private String accessToken;
    private String refreshToken;
}
