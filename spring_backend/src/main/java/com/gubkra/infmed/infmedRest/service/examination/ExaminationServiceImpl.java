package com.gubkra.infmed.infmedRest.service.examination;


import com.gubkra.infmed.infmedRest.domain.AppUser;
import com.gubkra.infmed.infmedRest.domain.HeartRateExaminaiton;
import com.gubkra.infmed.infmedRest.domain.TemperatureExamination;
import com.gubkra.infmed.infmedRest.repository.HeartRateExaminationRepository;
import com.gubkra.infmed.infmedRest.repository.TemperatureExaminationRepository;
import com.gubkra.infmed.infmedRest.service.ErrorMessages;
import com.gubkra.infmed.infmedRest.service.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExaminationServiceImpl implements ExaminationService{

    @Autowired
    private UserService userService;
    @Autowired
    private TemperatureExaminationRepository temperatureExaminationRepository;
    @Autowired
    private HeartRateExaminationRepository heartRateExaminationRepository;

    @Override
    public void saveTemperatureExamination(String patientUsername, Double value) {
        AppUser patient = userService.findByUsername(patientUsername);

        if (patient == null) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_PATIENT_USERNAME + ": " + patientUsername);
        }

        userService.checkPatientRole(patient);

        TemperatureExamination examination = new TemperatureExamination();
        examination.setValue(value);
        examination.setPatient(patient);
        examination.setDate(LocalDate.now());

        temperatureExaminationRepository.save(examination);
    }

    @Override
    public void saveHeartRateExamination(String patientUsername, Integer value) {
        AppUser patient = userService.findByUsername(patientUsername);

        if (patient == null) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_PATIENT_USERNAME + ": " + patientUsername);
        }

        userService.checkPatientRole(patient);

        HeartRateExaminaiton examination = new HeartRateExaminaiton();
        examination.setValue(value);
        examination.setPatient(patient);
        examination.setDate(LocalDate.now());

        heartRateExaminationRepository.save(examination);
    }
}
