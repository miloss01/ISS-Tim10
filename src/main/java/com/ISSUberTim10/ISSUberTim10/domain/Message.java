package com.ISSUberTim10.ISSUberTim10.domain;

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
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // Sender, receiver - TODO
//    @ManyToOne
//    @JoinColumn(name = "sender_id")
//    private User sender;

//    @ManyToOne
//    @JoinColumn(name = "receiver_id")
//    private User receiver;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "time_sent")
    private LocalDateTime timeSent;

    @Enumerated
    @Column(name = "message_type")
    private MESSAGE_TYPE messageType;

    @Column(name = "ride_id", nullable = false)
    private long rideID;

    public enum MESSAGE_TYPE {
        support, ride, panic
    }

}