package com.hazem.AutomaticIrrigation.repository;

import com.hazem.AutomaticIrrigation.entity.PlotTimeSlot;
import com.hazem.AutomaticIrrigation.entity.TimeSlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface PlotTimeSlotRepository extends JpaRepository<PlotTimeSlot, Long> {


    List<PlotTimeSlot> findAllByNextIrrigationIsNull();

    List<PlotTimeSlot> findAllByNextIrrigationBetween(LocalDateTime from, LocalDateTime to);

    @Transactional
    @Modifying
    @Query("update PlotTimeSlot set timeSlotStatus = com.hazem.AutomaticIrrigation.entity.TimeSlotStatus.WAITING")
    void resetTimeSlotStatus();

    @Transactional
    @Modifying
    @Query("update PlotTimeSlot set timeSlotStatus = :timeSlotStatus where id =:timeSlotId")
    int updateTimeSlotStatus(Long timeSlotId, TimeSlotStatus timeSlotStatus);
}
