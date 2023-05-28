package com.hazem.AutomaticIrrigation.repository;

import com.hazem.AutomaticIrrigation.entity.Plot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlotRepository extends JpaRepository<Plot, Long> {
}
