package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteMessageDTO {
    @Size(max = 500, message = "Field (message) cannot be longer than 100 characters!")
    private String message;


}
