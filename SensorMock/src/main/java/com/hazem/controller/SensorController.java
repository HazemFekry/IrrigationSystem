package com.hazem.controller;

import com.hazem.dto.IrrigationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sensor")
public class SensorController {

    @PostMapping
    public ResponseEntity<Void> addPlot(@RequestBody IrrigationDTO irrigationDTO) {
        System.out.println(String.format("irrigating plot number [%d] with [%d] liters of water for [%d] minutes",
                irrigationDTO.plotNumber(), irrigationDTO.amountOfWater(), irrigationDTO.duration()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
