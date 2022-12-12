package com.ISSUberTim10.ISSUberTim10.ride;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "panic")
public class Panic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private AppUser appUser;

    @ManyToOne
    private Ride ride;

    private LocalDateTime panicTime;  //ne moze samo time jer je to rezervisana rec

    private String reason;

}