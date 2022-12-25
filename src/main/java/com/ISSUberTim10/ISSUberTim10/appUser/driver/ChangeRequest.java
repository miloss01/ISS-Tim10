package com.ISSUberTim10.ISSUberTim10.appUser.driver;

import lombok.*;
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

}
