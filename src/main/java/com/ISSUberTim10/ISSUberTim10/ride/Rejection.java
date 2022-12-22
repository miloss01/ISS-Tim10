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
@Table(name = "rejection")
public class Rejection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Ride ride;

    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser;

    private LocalDateTime rejectionTime;

}