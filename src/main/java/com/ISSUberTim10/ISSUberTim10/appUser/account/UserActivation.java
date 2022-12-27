package com.ISSUberTim10.ISSUberTim10.appUser.account;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user_activation")
public class UserActivation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String lastName;

    private String phone;

    private String email;

    private String password;

    private String profileImage;

    private String address;

    private LocalDateTime dateCreated;

    // Zivotni vek
    private LocalDateTime dateExpiration;

}