package com.ISSUberTim10.ISSUberTim10.appUser.account;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.PassengerRequestDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.ride.Ride;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
@Entity
@Table(name = "passenger")
@Inheritance(strategy = InheritanceType.JOINED)
// Passenger - class TODO
public class Passenger extends AppUser {
    @ManyToMany(mappedBy = "passengers")
    private Collection<Ride> rides;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "passengers_favourite_rides",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "ride_id"))
    private Collection<Ride> favourite_rides;

    public Passenger(UserDTO userResponseDTO) {
        this.id = userResponseDTO.getId();
        this.setEmail(userResponseDTO.getEmail());
    }

    public Passenger(PassengerRequestDTO passengerRequestDTO) {
        this.setName(passengerRequestDTO.getName());
        this.setLastName(passengerRequestDTO.getSurname());
        this.setEmail(passengerRequestDTO.getEmail());
        this.setPhone(passengerRequestDTO.getTelephoneNumber());
        this.setProfileImage(passengerRequestDTO.getProfilePicture());
        this.setAddress(passengerRequestDTO.getAddress());
    }
}