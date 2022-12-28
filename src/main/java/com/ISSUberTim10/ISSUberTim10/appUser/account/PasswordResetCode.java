package com.ISSUberTim10.ISSUberTim10.appUser.account;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "password_reset_code")
public class PasswordResetCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String email;
    private String code;
    private LocalDateTime dateExpiration;

}
