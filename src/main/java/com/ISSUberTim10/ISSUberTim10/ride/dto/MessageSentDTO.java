package com.ISSUberTim10.ISSUberTim10.ride.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageSentDTO {
    @NotNull(message = "Field (receiverId) is required!")
    private Long receiverId;
    @NotNull(message = "Field (message) is required!")
    @Size(max = 500, message = "Field (message) cannot be longer than 500 characters!")
    private String message;
    @NotNull(message = "Field (type) is required!")
    private String type;
    @NotNull(message = "Field (rideId) is required!")
    private Long rideId;
}
