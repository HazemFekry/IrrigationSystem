package com.hazem.AutomaticIrrigation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


// when the irrigation starts and ends per day and what the interval in days
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "crop_time_slot")
public class CropTimeSlot {

    @SequenceGenerator(
            name = "time_sequence",
            sequenceName = "crop_time_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "crop_time_sequence"
    )
    @Id
    private Long id;

    @Column(nullable = false)
    private LocalTime startTime;

    // duration in minutes
    @Column(nullable = false)
    private int duration;

    // 1 means irrigate every day, 2 means irrigate every 2 days
    @Column(nullable = false)
    private int intervalDays;


}
