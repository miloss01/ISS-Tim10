package com.ISSUberTim10.ISSUberTim10.appUser;

import com.ISSUberTim10.ISSUberTim10.appUser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user_activation")
public class UserActivation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private AppUser appUser;

    private LocalDateTime dateCreated;

    // Zivotni vek
    private LocalDateTime dateExpiration;

}