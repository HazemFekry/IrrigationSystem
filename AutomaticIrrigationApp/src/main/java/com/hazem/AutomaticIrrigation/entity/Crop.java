package com.hazem.AutomaticIrrigation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "crop")
public class Crop {

    @SequenceGenerator(
            name = "crop_sequence",
            sequenceName = "crop_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "crop_sequence"
    )
    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    // crop needed amount of water in liters
    @Column(nullable = false)
    private int amountOfWater;

    @OneToOne
    private CropTimeSlot cropTimeSlot;

}
