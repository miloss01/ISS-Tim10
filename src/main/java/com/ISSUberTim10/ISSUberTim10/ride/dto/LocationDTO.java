package com.ISSUberTim10.ISSUberTim10.ride.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LocationDTO {

    private String address;

    private double latitude;

    private double longitude;
}
