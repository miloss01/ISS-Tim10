package com.ISSUberTim10.ISSUberTim10.appUser.account;

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
@Table(name = "admin")
@Inheritance(strategy = InheritanceType.JOINED)
public class Admin extends AppUser {
}