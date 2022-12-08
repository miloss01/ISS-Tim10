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
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "departure_coordinates_id")
    private Coordinates departureCoordinates;

    @ManyToOne
    @JoinColumn(name = "destination_coordinates_id")
    private Coordinates destinationCoordinates;

    @Column(name = "mileage", nullable = false)
    private double mileage;

}