package com.hazem.AutomaticIrrigation.dto;

import lombok.Data;

@Data
public class PlotDTO {

    private Long plotNumber;

    private String cropName;

    private Long cropId;

    private Float area;


}
