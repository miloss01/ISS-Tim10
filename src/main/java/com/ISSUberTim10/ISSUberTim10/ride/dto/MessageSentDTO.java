package com.ISSUberTim10.ISSUberTim10.ride.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageSentDTO {
    private Long receiverId;
    private String message;
    private String type;
    private Long rideId;
}