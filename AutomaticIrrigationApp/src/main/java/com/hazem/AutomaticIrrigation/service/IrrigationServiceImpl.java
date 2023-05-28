package com.hazem.AutomaticIrrigation.service;

import com.hazem.AutomaticIrrigation.entity.PlotTimeSlot;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class IrrigationServiceImpl implements IrrigationService {


    TimeSlotService timeSlotService;

    public void fillNextIrrigation() {
        List<PlotTimeSlot> timeSlots = timeSlotService.getTimeSlotsWithoutNextIrrigation();
        if (!timeSlots.isEmpty()) {
            List<PlotTimeSlot> timeSlotsWithNextIrrigation = new ArrayList<>();
            for (PlotTimeSlot timeSlot : timeSlots) {
                LocalDateTime nextIrrigation = calculateNextIrrigation(timeSlot.getCropTimeSlot().getStartTime());
                timeSlot.setNextIrrigation(nextIrrigation);
                timeSlotsWithNextIrrigation.add(timeSlot);
            }
            timeSlotService.saveTimeSlotList(timeSlotsWithNextIrrigation);
            log.info(timeSlotsWithNextIrrigation.size() + " timeslots has next irrigation configured");
        }
    }

    private LocalDateTime calculateNextIrrigation(LocalTime irrigationTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate irrigationDate;
        if (now.toLocalTime().isAfter(irrigationTime)) {
            irrigationDate = now.plusDays(1).toLocalDate();
        } else {
            irrigationDate = now.toLocalDate();
        }
        return LocalDateTime.of(irrigationDate, irrigationTime);
    }
}
