package com.ISSUberTim10.ISSUberTim10.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "driver")
@Inheritance(strategy = InheritanceType.JOINED)
public class Driver extends AppUser {

    // documentList - Mapiranje, kako se cuva u bazi TODO
    private HashSet<Document> documentList;

    // vehicle - Mapiranje TODO
    // one to one
    //@ToString.Exclude
    //private Vehicle vehicle;

    // rides - Mapiranje TODO
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Ride> rides;

}