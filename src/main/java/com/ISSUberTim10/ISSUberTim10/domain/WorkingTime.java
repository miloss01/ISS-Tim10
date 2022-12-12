package com.ISSUberTim10.ISSUberTim10.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "working_time")
// WorkingTime klasa TODO
public class WorkingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Driver driver;

}