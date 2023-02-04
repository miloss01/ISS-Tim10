package com.ISSUberTim10.ISSUberTim10.appUser.driver;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.ChangeRequestDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "change_request")
public class ChangeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String lastName;

    private String phone;

    private String email;

    @Lob
    private String profileImage;

    private String address;

    private boolean approved;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Driver driver;

    //private Collection<Document> documents;
    private String model;

    private String registrationPlate;

    private int numOfSeats;

    private boolean babyFlag;

    private boolean petsFlag;
    private LocalDateTime dateCreated;

    @Enumerated(EnumType.STRING)
    private Vehicle.VEHICLE_TYPE vehicleType;

    public ChangeRequest(ChangeRequestDTO requestDTO, Driver driver) {
        this.id = 0L;
        this.name = requestDTO.getUserDTO().getName();
        this.lastName = requestDTO.getUserDTO().getSurname();
        this.phone = requestDTO.getUserDTO().getTelephoneNumber();
        this.email = requestDTO.getUserDTO().getEmail();
        this.profileImage = requestDTO.getUserDTO().getProfilePicture();
        this.address = requestDTO.getUserDTO().getAddress();
        this.approved = false;
        this.driver = driver;
        this.model = requestDTO.getVehicleDTO().getModel();
        this.registrationPlate = requestDTO.getVehicleDTO().getLicenseNumber();
        this.numOfSeats = requestDTO.getVehicleDTO().getPassengerSeats();
        this.babyFlag = requestDTO.getVehicleDTO().getBabyTransport();
        this.petsFlag = requestDTO.getVehicleDTO().getPetTransport();
        this.vehicleType = Vehicle.VEHICLE_TYPE.valueOf(requestDTO.getVehicleDTO().getVehicleType());
        this.dateCreated = LocalDateTime.now();
    }
}
