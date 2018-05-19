package com.gubkra.infmed.infmedRest.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TemperatureExaminationDTO {
    private Long id;
    private LocalDate date;
    private Double value;
    private String type = "temperature";

}
