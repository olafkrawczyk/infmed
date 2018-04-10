package com.gubkra.infmed.infmedRest.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Data
@Entity
public class HeartRateExaminaiton extends MedicalExamination {
    private Integer value;
    @Lob
    private Integer[] rawData;

}
