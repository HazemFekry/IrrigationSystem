package com.hazem.AutomaticIrrigation.repository;

import com.hazem.AutomaticIrrigation.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CropRepository extends JpaRepository<Crop, Long> {

    Optional<Crop> findByName(String cropName);
}
