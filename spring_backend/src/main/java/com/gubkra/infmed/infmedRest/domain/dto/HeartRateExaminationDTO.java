package com.gubkra.infmed.infmedRest.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HeartRateExaminationDTO {
    private Long id;
    private LocalDate date;
    private Integer value;
    private Integer[] rawData;
    private String type = "heart-rate";

    public Integer getValue() {
        return value;
    }

    public Integer[] getRawData() {
        return rawData;
    }
}
