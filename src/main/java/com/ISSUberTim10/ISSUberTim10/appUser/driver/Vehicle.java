package com.ISSUberTim10.ISSUberTim10.appUser.driver;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.VehicleType;
import com.ISSUberTim10.ISSUberTim10.ride.Coordinates;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // driver - Mapiranje TODO je l okej bidirekciona jer zato posto
    // one to one
    @OneToOne(mappedBy = "vehicle", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Driver driver;

    private String model;

    private String registrationPlate;

    private int numOfSeats;

    // currentCoordinates - Kako se cuvaju u bazi
    //@Transient
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Coordinates currentCoordinates;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private VehicleType vehicleType;

    private boolean babyFlag;

    private boolean petsFlag;

    //private ArrayList<Review> reviews;

    public enum VEHICLE_TYPE {
        standard(120),
        luxury(180),
        van(220);

        public final double pricePerKilometer;

        VEHICLE_TYPE(double price) {
            this.pricePerKilometer = price;
        }
    }

}