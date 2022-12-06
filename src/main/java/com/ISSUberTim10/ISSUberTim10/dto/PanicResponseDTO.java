package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PanicResponseDTO {
    private Long id;
    private UserExpandedDTO user;
    private RideDTO ride;
    private String reason;
    private String time;


}
