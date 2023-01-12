package com.ISSUberTim10.ISSUberTim10.ride;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "departure_destination")
public class DepartureDestination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departure_id")
    private Location departure;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_id")
    private Location destination;

    public DepartureDestination(Location departure, Location destination) {
        this.departure = departure;
        this.destination = destination;
    }

}
