package com.gubkra.infmed.infmedRest.domain;


import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class HeartRateExaminaiton extends MedicalExamination {
    private int value;
}
