package com.hazem.AutomaticIrrigation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plot")
public class Plot {

    @SequenceGenerator(
            name = "plot_sequence",
            sequenceName = "plot_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "plot_sequence"
    )
    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private Long plotNumber;

    @ManyToOne
    private Crop crop;

    @Column(nullable = false)
    private float area;

    @OneToOne
    PlotTimeSlot timeSlot;

}
