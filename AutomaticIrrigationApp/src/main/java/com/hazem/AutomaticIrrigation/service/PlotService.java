package com.hazem.AutomaticIrrigation.service;

import com.hazem.AutomaticIrrigation.dto.PlotDTO;
import com.hazem.AutomaticIrrigation.entity.Plot;

import java.util.List;

public interface PlotService {

    Plot savePlot(Plot plot) ;

    void savePlotDTO(PlotDTO plot) ;

    void configurePlot(Long id, PlotDTO plot);

    List<Plot> getAllPlots();
}
