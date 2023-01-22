package com.ISSUberTim10.ISSUberTim10.ride;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private HashMap<LocalDate, Double> values;
    private double total;
    private double average;
}
