package com.hazem.AutomaticIrrigation.service;

import com.hazem.AutomaticIrrigation.dto.PlotDTO;
import com.hazem.AutomaticIrrigation.entity.Crop;
import com.hazem.AutomaticIrrigation.entity.Plot;
import com.hazem.AutomaticIrrigation.entity.PlotTimeSlot;
import com.hazem.AutomaticIrrigation.exception.NotFoundException;
import com.hazem.AutomaticIrrigation.repository.PlotRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class PlotServiceImpl implements PlotService {
    PlotRepository plotRepository;
    CropService cropService;

    TimeSlotService timeSlotService;

    public Plot savePlot(Plot plot) {
        return plotRepository.save(plot);
    }

    @Override
    public void savePlotDTO(PlotDTO plotDTO) {
        Crop crop = cropService.getCropByIdOrName(plotDTO.getCropId(), plotDTO.getCropName());
        PlotTimeSlot timeSlot = timeSlotService.createTimeSlot(crop);
        Plot plot = Plot.builder()
                .plotNumber(plotDTO.getPlotNumber())
                .area(plotDTO.getArea())
                .crop(crop)
                .timeSlot(timeSlot)
                .build();
        savePlot(plot);
        log.info("new plot with [" + plot.getId() + "] was saved");
        log.trace("created plot :" + plot);
    }

    @Override
    public void configurePlot(Long id, PlotDTO plotDTO) {
        Plot plot = getPlotById(id);
        plot = updatePlotFromPlotDTO(plot, plotDTO);
        Plot updatedPlot = plotRepository.save(plot);
        log.info("plot with [" + id + "] updated");
        log.trace("updated plot :" + updatedPlot);
    }

    private Plot updatePlotFromPlotDTO(Plot plot, PlotDTO plotDTO) {
        if (plotDTO.getPlotNumber() != null) {
            plot.setPlotNumber(plotDTO.getPlotNumber());
        }
        if (plotDTO.getArea() != null) {
            plot.setArea(plotDTO.getArea());
        }
        if (plotDTO.getCropName() != null || plotDTO.getCropId() != null) {
            Crop crop = cropService.getCropByIdOrName(plotDTO.getCropId(), plotDTO.getCropName());
            PlotTimeSlot timeSlot = timeSlotService.updateTimeSlot(plot.getTimeSlot().getId(), crop);
            plot.setCrop(crop);
            plot.setTimeSlot(timeSlot);
        }
        return plot;
    }

    private Plot getPlotById(Long plotId) {
        Plot plot = plotRepository.findById(plotId).orElseThrow(
                () -> new NotFoundException("Plot id [" + plotId + "] not found"));
        log.info("plot with id [" + plotId + "] found");
        return plot;
    }

    @Override
    public List<Plot> getAllPlots() {
        List<Plot> plots = plotRepository.findAll();
        log.info("get all plot returned " + plots.size() + " plots");
        return plots;
    }
}
