package com.ISSUberTim10.ISSUberTim10.ride;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.VehicleType;
import com.ISSUberTim10.ISSUberTim10.ride.DepartureDestination;
import com.ISSUberTim10.ISSUberTim10.ride.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "favorite_locations")
public class FavoriteLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String favoriteName;

    @ManyToMany (cascade = CascadeType.ALL)
    private List<DepartureDestination> locations;

    @ManyToMany
    private List<Passenger> passengers;

    private long makerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private VehicleType vehicleType;

    private boolean babyTransport;

    private boolean petTransport;


}
