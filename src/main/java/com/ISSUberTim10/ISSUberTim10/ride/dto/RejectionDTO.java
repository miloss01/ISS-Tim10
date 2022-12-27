package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.ride.Rejection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RejectionDTO {
    private String reason;
    private String timeOfRejection;

    public RejectionDTO(Rejection rejection) {
        this.reason = rejection.getReason();
        this.timeOfRejection = rejection.getRejectionTime().toString();
    }
}
