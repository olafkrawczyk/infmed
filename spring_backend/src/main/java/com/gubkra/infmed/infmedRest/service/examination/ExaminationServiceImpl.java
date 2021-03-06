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

    private final UserService userService;
    private final TemperatureExaminationRepository temperatureExaminationRepository;
    private final HeartRateExaminationRepository heartRateExaminationRepository;

    @Autowired
    public ExaminationServiceImpl(UserService userService, TemperatureExaminationRepository temperatureExaminationRepository, HeartRateExaminationRepository heartRateExaminationRepository) {
        this.userService = userService;
        this.temperatureExaminationRepository = temperatureExaminationRepository;
        this.heartRateExaminationRepository = heartRateExaminationRepository;
    }

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
    public void saveHeartRateExamination(String patientUsername, HeartRateExaminaiton examination) {
        AppUser patient = userService.findByUsername(patientUsername);

        if (patient == null) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_PATIENT_USERNAME + ": " + patientUsername);
        }

        userService.checkPatientRole(patient);

        examination.setPatient(patient);
        examination.setDate(LocalDate.now());

        heartRateExaminationRepository.save(examination);
    }
}
