package com.ISSUberTim10.ISSUberTim10.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.InheritanceType.JOINED;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@TableGenerator(name="appUser_id_generator", table="primary_keys", pkColumnName="key_pk", pkColumnValue="appUser", valueColumnName="value_pk")
@Inheritance(strategy=JOINED)
public abstract class AppUser {

//    @Id
//    @SequenceGenerator(name = "SeqGenUser", sequenceName = "SeqUser", initialValue = 1, allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqGenUser")
//    private Long id;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_id_generator")
    protected Long id;

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