package com.gubkra.infmed.infmedRest.service.examination;

import com.gubkra.infmed.infmedRest.domain.HeartRateExaminaiton;

public interface ExaminationService {
    void saveTemperatureExamination(String patientUsername, Double value);
    void saveHeartRateExamination(String patientUsername, HeartRateExaminaiton value);
}
