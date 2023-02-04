package com.ISSUberTim10.ISSUberTim10.ride;

import com.ISSUberTim10.ISSUberTim10.ride.dto.DepartureDestinationLocationsDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_coordinates_id")
    @ToString.Exclude
    private Coordinates departureCoordinates;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_coordinates_id")
    @ToString.Exclude
    private Coordinates destinationCoordinates;

    private double mileage;

    private int orderr;

    @ManyToMany(mappedBy = "routes")
    private Collection<Ride> rides;

    public Route(DepartureDestinationLocationsDTO routeDto, Double distance) {
        this.departureCoordinates = new Coordinates(routeDto.getDeparture());
        this.destinationCoordinates = new Coordinates(routeDto.getDestination());
        this.mileage = distance;
    }
}