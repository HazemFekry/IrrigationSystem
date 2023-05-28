package com.hazem.AutomaticIrrigation.service;

import com.hazem.AutomaticIrrigation.entity.Crop;

public interface CropService {

    Crop getCropById(long cropId);

    Crop getCropByName(String cropName);

    Crop getCropByIdOrName(Long cropId, String cropName);
}
