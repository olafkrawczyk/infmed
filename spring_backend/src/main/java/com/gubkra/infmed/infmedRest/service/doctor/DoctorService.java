package com.gubkra.infmed.infmedRest.service.doctor;

import java.util.UUID;

public interface DoctorService {
    void registerPatient(UUID doctor_uuid, UUID patient_UUID) throws IllegalArgumentException;
    void removePatient(UUID doctor_uuid, UUID patient_UUID) throws IllegalArgumentException;
    void saveTemperatureExamination(String patientUsername, Double value);
    void saveHeartRateExamination(String patientUsername, Integer value);
}
