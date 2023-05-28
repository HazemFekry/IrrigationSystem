package com.hazem.AutomaticIrrigation.service;

import com.hazem.AutomaticIrrigation.entity.Crop;
import com.hazem.AutomaticIrrigation.entity.PlotTimeSlot;
import com.hazem.AutomaticIrrigation.entity.TimeSlotStatus;
import com.hazem.AutomaticIrrigation.repository.PlotTimeSlotRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static com.hazem.AutomaticIrrigation.AppConfig.TIMEZONE_OFFSET;

@Service
@AllArgsConstructor
@Slf4j
public class TimeSlotServiceImpl implements TimeSlotService {

    PlotTimeSlotRepository plotTimeSlotRepository;

    @Override
    public List<PlotTimeSlot> getTimeSlotsWithoutNextIrrigation() {
        List<PlotTimeSlot> timeSlotsWithoutNextIrrigation = plotTimeSlotRepository.findAllByNextIrrigationIsNull();
        log.info("get timeslots without next irrigation returned " + timeSlotsWithoutNextIrrigation.size() + " slot");
        return timeSlotsWithoutNextIrrigation;
    }

    @Override
    public void saveTimeSlotList(List<PlotTimeSlot> timeSlots) {
        plotTimeSlotRepository.saveAll(timeSlots);
        log.info(timeSlots.size() + " timeSlots were saved");
    }

    @Override
    public List<PlotTimeSlot> getTimeSlotPerDay(LocalDate date) {
        List<PlotTimeSlot> timeSlots = plotTimeSlotRepository.findAllByNextIrrigationBetween(date.atStartOfDay()
                , date.plusDays(1).atStartOfDay());
        log.info("get timeslots Per day returned " + timeSlots.size() + " slot");
        return timeSlots;
    }

    @Override
    public List<PlotTimeSlot> getTimeSlotForTheRestOfTheDay() {
        LocalDateTime now = Instant.now().atOffset(ZoneOffset.ofHours(TIMEZONE_OFFSET)).toLocalDateTime();
        List<PlotTimeSlot> timeSlots = getTimeSlotBetweenTime(now, now.plusDays(1).toLocalDate().atStartOfDay());
        return timeSlots;
    }

    private List<PlotTimeSlot> getTimeSlotBetweenTime(LocalDateTime from, LocalDateTime to) {
        log.debug("get timeslots between time was called with args " + from.toString() + ", " + to.toString());
        List<PlotTimeSlot> timeSlots = plotTimeSlotRepository.findAllByNextIrrigationBetween(from, to);
        log.info("get timeslots between time returned " + timeSlots.size() + " slot");
        return timeSlots;
    }

    public void saveTimeSlot(PlotTimeSlot plotTimeSlot) {
        PlotTimeSlot timeSlot = plotTimeSlotRepository.save(plotTimeSlot);
        log.debug("time slot saved");
        log.trace("saved time slot :" + timeSlot);
    }

    @Override
    public void resetTimeSlotStatus() {
        plotTimeSlotRepository.resetTimeSlotStatus();
        log.info("time slots status has been reset");
    }

    @Override
    public PlotTimeSlot createTimeSlot(Crop crop) {
        PlotTimeSlot timeSlot = PlotTimeSlot.builder()
                .cropTimeSlot(crop.getCropTimeSlot())
                .timeSlotStatus(TimeSlotStatus.WAITING)
                .build();
        PlotTimeSlot createdTimeSlot = plotTimeSlotRepository.saveAndFlush(timeSlot);
        log.info("time slots created");
        log.trace("created time slot : " + createdTimeSlot);
        return createdTimeSlot;
    }

    public PlotTimeSlot updateTimeSlot(Long id, Crop crop) {
        PlotTimeSlot timeSlot = PlotTimeSlot.builder()
                .id(id)
                .cropTimeSlot(crop.getCropTimeSlot())
                .timeSlotStatus(TimeSlotStatus.WAITING)
                .build();
        PlotTimeSlot createdTimeSlot = plotTimeSlotRepository.saveAndFlush(timeSlot);
        log.info("time slots [" + id + "] updated");
        log.trace("created time slot : " + createdTimeSlot);
        return createdTimeSlot;
    }

    @Override
    public void updateTimeSlotStatus(Long timeSlotId, TimeSlotStatus timeSlotStatus) {
        int updatedRecords = plotTimeSlotRepository.updateTimeSlotStatus(timeSlotId, timeSlotStatus);
        if (updatedRecords > 0)
            log.info("time slots [" + timeSlotId + "] updated to status [" + timeSlotStatus + "]");
    }

}
