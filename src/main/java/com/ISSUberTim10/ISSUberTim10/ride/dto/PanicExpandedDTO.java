package com.ISSUberTim10.ISSUberTim10.ride.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserExpandedDTO;
import com.ISSUberTim10.ISSUberTim10.ride.Panic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PanicExpandedDTO {

    private Integer id;
    @Valid
    private UserExpandedDTO user;
    @Valid
    private RideDTO ride;
    private String time;
    @NotBlank(message = "Field (reason) is required!")
    @Size(max = 500, message = "Field (reason) cannot be longer than 500 characters!")
    private String reason;

    public PanicExpandedDTO(Panic panic) {
        this.id = Math.toIntExact(panic.getId());
        this.user = new UserExpandedDTO(panic.getAppUser());
        this.ride = new RideDTO(panic.getRide());
        this.time =panic.getPanicTime().toString();
        this.reason = panic.getReason();
    }
}
