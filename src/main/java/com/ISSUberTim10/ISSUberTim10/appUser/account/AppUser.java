package com.ISSUberTim10.ISSUberTim10.appUser.account;

import com.ISSUberTim10.ISSUberTim10.appUser.Role;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String name;

    private String lastName;

    private String phone;

    private String email;

    private String password;

    private String profileImage;

    private String address;

    private boolean blockedFlag;

    private boolean activeFlag;

    private Role role;

}