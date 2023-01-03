package com.ISSUberTim10.ISSUberTim10.appUser.account;

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
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne
    private AppUser sender;

    @ManyToOne
    private AppUser receiver;

    private String text;

    private LocalDateTime timeSent;

    @Enumerated(EnumType.STRING)
    private MESSAGE_TYPE messageType;

    private long rideId;

    public enum MESSAGE_TYPE {
        support, ride, panic
    }

}