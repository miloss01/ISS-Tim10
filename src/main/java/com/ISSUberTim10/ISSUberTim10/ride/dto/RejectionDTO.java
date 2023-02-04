package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.ride.Rejection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RejectionDTO {
    @NotBlank(message = "Field (reason) is required!")
    @Size(max = 500, message = "Field (reason) cannot be longer than 500 characters!")
    private String reason;
    @NotNull(message = "Field (reason) is required!")
    private String timeOfRejection;

    public RejectionDTO(Rejection rejection) {
        this.reason = rejection.getReason();
        this.timeOfRejection = rejection.getRejectionTime().toString();
    }
}
