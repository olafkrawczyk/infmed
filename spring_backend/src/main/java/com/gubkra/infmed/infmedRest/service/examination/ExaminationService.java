package com.gubkra.infmed.infmedRest.service.examination;

public interface ExaminationService {
    void saveTemperatureExamination(String patientUsername, Double value);
    void saveHeartRateExamination(String patientUsername, Integer value);
}
