package com.ISSUberTim10.ISSUberTim10.ride.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NotificationDTO {

    private String message;

    private Integer rideId;

    private String reason;

}
