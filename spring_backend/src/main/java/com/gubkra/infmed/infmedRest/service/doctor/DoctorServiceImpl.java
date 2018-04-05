package com.gubkra.infmed.infmedRest.service.doctor;

import com.gubkra.infmed.infmedRest.domain.AppUser;
import com.gubkra.infmed.infmedRest.domain.HeartRateExaminaiton;
import com.gubkra.infmed.infmedRest.domain.Role;
import com.gubkra.infmed.infmedRest.domain.TemperatureExamination;
import com.gubkra.infmed.infmedRest.repository.HeartRateExaminationRepository;
import com.gubkra.infmed.infmedRest.repository.RoleRepository;
import com.gubkra.infmed.infmedRest.repository.TemperatureExaminationRepository;
import com.gubkra.infmed.infmedRest.service.domain.user.UserService;
import com.gubkra.infmed.infmedRest.utils.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements DoctorService {
    private static final Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TemperatureExaminationRepository temperatureExaminationRepository;

    @Autowired
    private HeartRateExaminationRepository heartRateExaminationRepository;

    @Override
    public void registerPatient(UUID doctor_uuid, UUID patient_uuid) throws IllegalArgumentException {
        Optional<AppUser> doctor = userService.findById(doctor_uuid);
        Optional<AppUser> patient = userService.findById(patient_uuid);

        doctor.ifPresent(this::checkDoctorRole);
        patient.ifPresent(this::checkPatientRole);

        if (!doctor.get().getPatients().contains(patient.get())) {
            doctor.ifPresent((d) -> d.getPatients().add(patient.get()));
            userService.addItem(doctor.get());
            logger.info("Registered patient [" + patient.get().getUsername() + "] to doctor ["
                    + doctor.get().getUsername() + "]");
        } else {
            throw new IllegalArgumentException(DoctorErrorMessages.PATIENT_ALREADY_ASSIGNED);
        }
    }

    @Override
    public void removePatient(UUID doctor_uuid, UUID patient_uuid) throws IllegalArgumentException {
        Optional<AppUser> doctor = userService.findById(doctor_uuid);
        Optional<AppUser> patient = userService.findById(patient_uuid);

        doctor.ifPresent(this::checkDoctorRole);
        patient.ifPresent(this::checkPatientRole);

        if (doctor.get().getPatients().contains(patient.get())) {
            doctor.ifPresent((d) -> d.getPatients().remove(patient.get()));
            userService.addItem(doctor.get());
            logger.info("Removed patient [" + patient.get().getUsername() + "] from doctor ["
                    + doctor.get().getUsername() + "]");
        } else {
            throw new IllegalArgumentException(DoctorErrorMessages.PATIENT_ISNT_ASSIGNED);
        }
    }

    @Override
    public void saveTemperatureExamination(String patientUsername, Double value) {
        AppUser patient = userService.findByUsername(patientUsername);

        if (patient == null) {
            throw new IllegalArgumentException(DoctorErrorMessages.INVALID_PATIENT_USERNAME + ": " + patientUsername);
        }

        checkPatientRole(patient);

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
            throw new IllegalArgumentException(DoctorErrorMessages.INVALID_PATIENT_USERNAME + ": " + patientUsername);
        }

        checkPatientRole(patient);

        HeartRateExaminaiton examination = new HeartRateExaminaiton();
        examination.setValue(value);
        examination.setPatient(patient);
        examination.setDate(LocalDate.now());

        heartRateExaminationRepository.save(examination);
    }

    private void checkPatientRole(AppUser patient) {
        Role patientRole = roleRepository.findByName(SecurityConstants.ROLE_PATIENT);
        if (!patient.getRoles().contains(patientRole)) {
            throw new IllegalArgumentException(DoctorErrorMessages.INVALID_PATIENT_ROLE);
        }
    }

    private void checkDoctorRole(AppUser doctor) {
        Role doctorRole = roleRepository.findByName(SecurityConstants.ROLE_DOCTOR);

        if (!doctor.getRoles().contains(doctorRole)) {
            throw new IllegalArgumentException(DoctorErrorMessages.INVALID_DOCTOR_ROLE);
        }
    }
}
