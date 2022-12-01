package com.ISSUberTim10.ISSUberTim10.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class User {

    @Id
    @SequenceGenerator(name = "SeqGenUser", sequenceName = "SeqUser", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqGenUser")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="last_name")
    private String lastName;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="profile_image")
    private String profileImage;

    @Column(name="address")
    private String address;

    @Column(name="blocked_flag")
    private boolean blockedFlag;

    @Column(name="active_flag")
    private boolean activeFlag;

}