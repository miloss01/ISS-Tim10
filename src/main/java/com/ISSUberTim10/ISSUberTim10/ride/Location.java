package com.ISSUberTim10.ISSUberTim10.ride;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String address;

    private double latitude;

    private double longitude;

    public Location(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}