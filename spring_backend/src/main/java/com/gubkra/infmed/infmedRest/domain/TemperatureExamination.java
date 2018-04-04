package com.gubkra.infmed.infmedRest.domain;


import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class TemperatureExamination extends MedicalExamination {
    private double value;
}
