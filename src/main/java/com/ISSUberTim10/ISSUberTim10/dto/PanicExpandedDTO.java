package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PanicExpandedDTO {

    private Integer id;
    private UserExpandedDTO user;
    private RideDTO ride;
    private String time;
    private String reason;

}