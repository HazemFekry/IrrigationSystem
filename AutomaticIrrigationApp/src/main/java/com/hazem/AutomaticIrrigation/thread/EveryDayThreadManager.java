package com.hazem.AutomaticIrrigation.thread;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hazem.AutomaticIrrigation.entity.PlotTimeSlot;
import com.hazem.AutomaticIrrigation.service.SchedulerService;
import com.hazem.AutomaticIrrigation.service.SensorService;
import com.hazem.AutomaticIrrigation.service.TimeSlotService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Component
public class EveryDayThreadManager {

    SchedulerService schedulerService;
    TimeSlotService timeSlotService;

    SensorService sensorService;

    @Scheduled(cron = "0 0 0 * * *")
    public void runTask() {
        timeSlotService.resetTimeSlotStatus();
        List<PlotTimeSlot> timeSlotsForToday = timeSlotService.getTimeSlotPerDay(LocalDate.now());
        schedulerService.setupTimeSlotThreads(timeSlotsForToday);

    }

}
