package com.ISSUberTim10.ISSUberTim10.appUser.account;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
//@ToString
@Entity
@Table(name = "passenger")
@Inheritance(strategy = InheritanceType.JOINED)
public class Passenger extends AppUser {

    public Passenger(UserDTO userResponseDTO) {
        this.id = userResponseDTO.getId();
        this.setEmail(userResponseDTO.getEmail());
    }
}