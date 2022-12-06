package com.ISSUberTim10.ISSUberTim10.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ride")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double price;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

//    passengers - Mapiranje TODO
//    private ArrayList<Passenger> passengersList;

//    route - Mapiranje TODO
//    private Route route;

    private int estimatedTimeMinutes;

    private RIDE_STATUS rideStatus;

//    rejection - Mapiranje TODO
//    @ToString.Exclude
//    private Rejection rejection;

    @Column(name = "panic_flag", nullable = false)
    private boolean panicFlag;

    @Column(name = "baby_flag", nullable = false)
    private boolean babyFlag;

    @Column(name = "pets_flag", nullable = false)
    private boolean petsFlag;

    @Enumerated
    @Column(name = "vehicle_type")
    private Vehicle.VEHICLE_TYPE vehicleType;

//    reviews - Mapiranje TODO
//    private ArrayList<Review> reviews;

    public enum RIDE_STATUS {
        pending,
        accepted,
        rejected,
        active,
        finished
    }

}