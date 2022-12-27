package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationDTO {

    private String address;

    private double latitude;

    private double longitude;
}
