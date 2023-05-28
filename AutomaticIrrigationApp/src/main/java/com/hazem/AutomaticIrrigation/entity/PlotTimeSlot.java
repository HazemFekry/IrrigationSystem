package com.hazem.AutomaticIrrigation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


// when the irrigation starts and ends per day and what the interval in days
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "time_slot")
public class PlotTimeSlot {

    @SequenceGenerator(
            name = "time_sequence",
            sequenceName = "time_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "time_sequence"
    )
    @Id
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable=false)
    CropTimeSlot cropTimeSlot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeSlotStatus timeSlotStatus;

    private LocalDateTime nextIrrigation;

    @OneToOne
    private Plot plot;

}
