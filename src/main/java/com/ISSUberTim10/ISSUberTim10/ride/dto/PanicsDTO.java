package com.ISSUberTim10.ISSUberTim10.ride.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PanicsDTO {

    private Integer totalCount;
    @Valid
    List<PanicExpandedDTO> results;

}
