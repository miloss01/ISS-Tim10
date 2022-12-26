package com.ISSUberTim10.ISSUberTim10.ride;

import com.ISSUberTim10.ISSUberTim10.ride.dto.LocationDTO;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private double latitude;

    private double longitude;

    @Column
    private String address;

    public Coordinates(LocationDTO locationDTO) {
        this(null, locationDTO.getLatitude(), locationDTO.getLongitude(), locationDTO.getAddress());
    }

}