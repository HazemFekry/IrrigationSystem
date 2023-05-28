package com.hazem.AutomaticIrrigation.service;

import com.hazem.AutomaticIrrigation.entity.Crop;
import com.hazem.AutomaticIrrigation.entity.PlotTimeSlot;
import com.hazem.AutomaticIrrigation.entity.TimeSlotStatus;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotService {

    List<PlotTimeSlot> getTimeSlotsWithoutNextIrrigation();

    void saveTimeSlotList(List<PlotTimeSlot> timeSlots);

    List<PlotTimeSlot> getTimeSlotPerDay(LocalDate date);

    List<PlotTimeSlot> getTimeSlotForTheRestOfTheDay();

    void saveTimeSlot(PlotTimeSlot plotTimeSlot);

    void resetTimeSlotStatus();

    PlotTimeSlot createTimeSlot(Crop crop);

    PlotTimeSlot updateTimeSlot(Long id, Crop crop);

    void updateTimeSlotStatus(Long timeSlotId, TimeSlotStatus timeSlotStatus);
}
