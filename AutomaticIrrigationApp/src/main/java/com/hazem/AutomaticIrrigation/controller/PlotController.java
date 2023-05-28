package com.hazem.AutomaticIrrigation.controller;

import com.hazem.AutomaticIrrigation.dto.PlotDTO;
import com.hazem.AutomaticIrrigation.entity.Plot;
import com.hazem.AutomaticIrrigation.service.PlotService;
import com.hazem.AutomaticIrrigation.service.SchedulerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/plot")
@AllArgsConstructor
@Slf4j
public class PlotController {

    PlotService plotService;

    SchedulerService schedulerService;

    @PostMapping
    public ResponseEntity<Void> addPlot(@RequestBody PlotDTO plot) {
        log.info("add plot api called with plot dto: " + plot.toString());
        plotService.savePlotDTO(plot);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> configurePlot(@PathVariable Long id, @RequestBody PlotDTO plotDTO) {
        log.info("configure plot api called with plot id: " + id + ", plotDTO:" + plotDTO.toString());
        plotService.configurePlot(id, plotDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Plot>> getAllPlots() {
        log.info("get all plot api called");
        List<Plot> allPlots = plotService.getAllPlots();
        return ResponseEntity.ok(allPlots);
    }


    @DeleteMapping
    public ResponseEntity<Void> schedueleThread() {
        log.debug("schedueleThread");
        log.info("schedueleThread");
        log.error("schedueleThread");
        System.out.println("schedueleThread p");
        schedulerService.setupTimeSlotThreadsForToday();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
