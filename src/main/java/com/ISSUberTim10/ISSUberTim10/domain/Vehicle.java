package com.ISSUberTim10.ISSUberTim10.domain;

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

    // driver - Mapiranje TODO
    // one to one
    //@ToString.Exclude
    //private Driver driver;

    private String model;

    private VEHICLE_TYPE type;

    private String registrationPlate;

    private int numOfSeats;

    // currentCoordinates - Kako se cuvaju u bazi TODO
    @Transient
    private Coordinates currentCoordinates;

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