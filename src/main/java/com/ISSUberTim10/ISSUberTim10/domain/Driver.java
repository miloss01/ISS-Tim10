package com.ISSUberTim10.ISSUberTim10.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="driver")
public class Driver extends User {

    // documentList - Mapiranje, kako se cuva u bazi TODO
    private ArrayList<Document> documentList;

    // vehicle - Mapiranje TODO
    // one to one
    //@ToString.Exclude
    //private Vehicle vehicle;

    // rides - Mapiranje TODO
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private ArrayList<Ride> rides;

}