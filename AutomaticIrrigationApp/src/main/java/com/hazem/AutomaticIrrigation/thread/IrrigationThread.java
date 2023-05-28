package com.hazem.AutomaticIrrigation.thread;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hazem.AutomaticIrrigation.entity.Plot;
import com.hazem.AutomaticIrrigation.entity.PlotTimeSlot;
import com.hazem.AutomaticIrrigation.entity.TimeSlotStatus;
import com.hazem.AutomaticIrrigation.service.SensorService;
import com.hazem.AutomaticIrrigation.service.TimeSlotService;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class IrrigationThread implements Runnable {

    PlotTimeSlot timeSlot;

    TimeSlotService timeSlotService;

    SensorService sensorService;


    @Override
    public void run() {

        Plot plot = timeSlot.getPlot();
        long plotNumber = plot.getPlotNumber();
        int duration = timeSlot.getCropTimeSlot().getDuration();
        long amountOfWater = plot.getCrop().getAmountOfWater();

        try {
            sensorService.callSensor(plotNumber, duration, amountOfWater,plot.getId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        timeSlot.setTimeSlotStatus(TimeSlotStatus.IRRIGATED);
        timeSlotService.saveTimeSlot(timeSlot);

    }
}
