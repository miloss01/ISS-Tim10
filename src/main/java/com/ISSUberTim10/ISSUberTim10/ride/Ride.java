package com.ISSUberTim10.ISSUberTim10.ride;

import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.UserDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Driver;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.Vehicle;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.VehicleType;
import com.ISSUberTim10.ISSUberTim10.ride.dto.DepartureDestinationLocationsDTO;
import com.ISSUberTim10.ISSUberTim10.ride.dto.RideCreationDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
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
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "passengers_rides",
            joinColumns = @JoinColumn(name = "ride_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
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

    @Enumerated(EnumType.STRING)
    private RIDE_STATUS rideStatus;


    @Column(name = "panic_flag", nullable = false)
    private boolean panicFlag;

    @Column(name = "baby_flag", nullable = false)
    private boolean babyFlag;

    @Column(name = "pets_flag", nullable = false)
    private boolean petsFlag;

    @OneToOne(mappedBy = "ride", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Rejection rejection;

    public Ride(RideCreationDTO rideCreation) {
        ArrayList<Passenger> passengers = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for (UserDTO userResponseDTO: rideCreation.getPassengers()) {
            if (!names.contains(userResponseDTO.getEmail())) {
                names.add(userResponseDTO.getEmail());
                passengers.add(new Passenger(userResponseDTO));
            }
        }
        ArrayList<Route> routes = new ArrayList<>();
        for(DepartureDestinationLocationsDTO routeDto: rideCreation.getLocations()){
            routes.add(new Route(routeDto));
        }
        this.passengers = passengers;
        this.routes = routes;
        try {
            this.startTime = LocalDateTime.parse(rideCreation.getScheduleTime().replace('T', ' '), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception ex) {this.startTime = LocalDateTime.now();}
        this.petsFlag = rideCreation.isPetTransport();
        this.babyFlag = rideCreation.isBabyTransport();
        Driver dummy = new Driver();
        Vehicle dummyVehicle = new Vehicle();
        dummyVehicle.setVehicleType(new VehicleType(0L, Vehicle.VEHICLE_TYPE.valueOf(rideCreation.getVehicleType()), 0));
        dummy.setVehicle(dummyVehicle);
        this.driver = dummy;
        this.endTime = this.getStartTime().plusMinutes(this.estimatedTimeMinutes);
    }

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

    public String getVehicleType() {
        return this.getDriver().getVehicle().getVehicleType().getName().toString();
    }

    public boolean isBabyTransport() {
        return this.getDriver().getVehicle().isBabyFlag();
    }

    public boolean isPetTransport() {
        return this.getDriver().getVehicle().isPetsFlag();
    }

}