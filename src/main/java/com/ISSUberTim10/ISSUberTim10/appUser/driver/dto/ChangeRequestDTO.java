package com.ISSUberTim10.ISSUberTim10.appUser.driver.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserExpandedDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.ChangeRequest;
import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequestDTO {

    private UserExpandedDTO userDTO;
    private VehicleDTO vehicleDTO;
    private String date;

    public ChangeRequestDTO(ChangeRequest changeRequest) {
        this.userDTO = new UserExpandedDTO(changeRequest.getDriver().getId(), changeRequest.getName(),
                changeRequest.getLastName(), changeRequest.getProfileImage(), changeRequest.getPhone(),
                changeRequest.getEmail(), changeRequest.getAddress());
        this.vehicleDTO = new VehicleDTO(0, Math.toIntExact(changeRequest.getDriver().getId()),
                changeRequest.getVehicleType().toString(), changeRequest.getModel(),
                changeRequest.getRegistrationPlate(), new LocationDTO(), changeRequest.getNumOfSeats(),
                changeRequest.isBabyFlag(), changeRequest.isPetsFlag());
        this.date = changeRequest.getDateCreated().toString();
    }
}
