package com.ISSUberTim10.ISSUberTim10.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

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

    //jel ovde ide date i dont know
    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private double price;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

//    passengers - Mapiranje
//    private ArrayList<Passenger> passengersList;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "passengers_rides",
            joinColumns = @JoinColumn(name = "ride_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    private Collection<Passenger> passengers;

//    route - Mapiranje TODO
//    private Route route;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "routes_rides",
            joinColumns = @JoinColumn(name = "ride_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id"))
    private Collection<Route> routes;

    @Column
    private int estimatedTimeMinutes;

    @Column
    private RIDE_STATUS rideStatus;


    @Column(name = "panic_flag", nullable = false)
    private boolean panicFlag;

    @Column(name = "baby_flag", nullable = false)
    private boolean babyFlag;

    @Column(name = "pets_flag", nullable = false)
    private boolean petsFlag;

//    @Enumerated
//    @Column(name = "vehicle_type")
//    private Vehicle.VEHICLE_TYPE vehicleType;


    public enum RIDE_STATUS {
        pending,
        accepted,
        rejected,
        active,
        finished
    }

}