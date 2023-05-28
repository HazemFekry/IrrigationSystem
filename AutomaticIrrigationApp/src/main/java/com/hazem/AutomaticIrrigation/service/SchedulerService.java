package com.hazem.AutomaticIrrigation.service;

import com.hazem.AutomaticIrrigation.entity.PlotTimeSlot;
import com.hazem.AutomaticIrrigation.thread.IrrigationThread;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

import static com.hazem.AutomaticIrrigation.AppConfig.TIMEZONE_OFFSET;


@AllArgsConstructor
@Service
@Slf4j
public class SchedulerService {

    TaskScheduler taskScheduler;

    TimeSlotService timeSlotService;

    SensorService sensorService;


    public void setupTimeSlotThreadsForToday() {
        log.debug("scheduling today's time slot thread");
        List<PlotTimeSlot> timeSlotList = timeSlotService.getTimeSlotForTheRestOfTheDay();
        setupTimeSlotThreads(timeSlotList);
        log.info("Today's time slot threads has been scheduled");
    }

    public void setupTimeSlotThreads(List<PlotTimeSlot> timeSlotList) {
        for (PlotTimeSlot timeSlot : timeSlotList) {
            LocalDateTime taskDateTime = timeSlot.getNextIrrigation();
            Instant threadTime = taskDateTime.toInstant(ZoneOffset.ofHours(TIMEZONE_OFFSET));
            taskScheduler.schedule(new IrrigationThread(timeSlot, timeSlotService, sensorService), threadTime);
        }
    }

}
