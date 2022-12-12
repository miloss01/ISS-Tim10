package com.ISSUberTim10.ISSUberTim10.appUser.driver;

import com.ISSUberTim10.ISSUberTim10.appUser.AppUser;
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
@Table(name = "driver")
@Inheritance(strategy = InheritanceType.JOINED)
public class Driver extends AppUser {

    // documentList - Mapiranje, kako se cuva u bazi ---sacuvano u documents
    //private HashSet<Document> documentList;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    // rides - Mapiranje
//    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @ToString.Exclude
//    private Set<Ride> rides;

}