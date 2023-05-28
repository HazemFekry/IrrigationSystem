package com.hazem.AutomaticIrrigation.service;

import com.hazem.AutomaticIrrigation.entity.Crop;
import com.hazem.AutomaticIrrigation.exception.NotFoundException;
import com.hazem.AutomaticIrrigation.repository.CropRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CropServiceImpl implements CropService {
    CropRepository cropRepository;

    @Override
    public Crop getCropById(long cropId) {
        Crop crop = cropRepository.findById(cropId).orElseThrow(
                () -> new NotFoundException("Crop id [" + cropId + "] not found"));
        log.info("Crop with id [" + cropId + "] found");
        return crop;
    }

    @Override
    public Crop getCropByName(String cropName) {
        Crop crop = cropRepository.findByName(cropName).orElseThrow(
                () -> new NotFoundException("Crop with name [" + cropName + "] not found"));
        log.info("Crop with name [" + cropName + "] found");
        return crop;
    }

    @Override
    public Crop getCropByIdOrName(Long cropId, String cropName) {
        Crop crop;
        if (cropId != null && cropId != 0) {
            crop = getCropById(cropId);
        } else if (cropName != null && !cropName.isEmpty()) {
            crop = getCropByName(cropName);
        } else {
            throw new RuntimeException("crop info is missing");
        }
        return crop;
    }
}
