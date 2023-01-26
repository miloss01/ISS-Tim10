package com.ISSUberTim10.ISSUberTim10.ride.dto;


import com.ISSUberTim10.ISSUberTim10.ride.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ReportDTO {
    private HashMap<String, Double> values;
    private double total;
    private double average;

    public ReportDTO() {
        values = new HashMap<>();
        total = 0;
        average = 0;
    }

    public ReportDTO(Report report) {
        this.values = new HashMap<>();
        this.total = report.getTotal();
        this.average = report.getAverage();
        for (Map.Entry<LocalDate, Double> set: report.getValues().entrySet()) {
            this.values.put(set.getKey().toString(), set.getValue());
        }

    }
}
