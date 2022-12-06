package com.ISSUberTim10.ISSUberTim10.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "passenger")
@Inheritance(strategy = InheritanceType.JOINED)
// Passenger - class TODO
public class Passenger extends AppUser {
}