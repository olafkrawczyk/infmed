package com.gubkra.infmed.infmedRest.service.doctor;

import com.gubkra.infmed.infmedRest.domain.AppUser;
import com.gubkra.infmed.infmedRest.repository.HeartRateExaminationRepository;
import com.gubkra.infmed.infmedRest.repository.RoleRepository;
import com.gubkra.infmed.infmedRest.repository.TemperatureExaminationRepository;
import com.gubkra.infmed.infmedRest.service.ErrorMessages;
import com.gubkra.infmed.infmedRest.service.domain.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        doctor.ifPresent(userService::checkDoctorRole);
        patient.ifPresent(userService::checkPatientRole);

        if (!doctor.get().getPatients().contains(patient.get())) {
            doctor.ifPresent((d) -> d.getPatients().add(patient.get()));
            userService.addItem(doctor.get());
            logger.info("Registered patient [" + patient.get().getUsername() + "] to doctor ["
                    + doctor.get().getUsername() + "]");
        } else {
            throw new IllegalArgumentException(ErrorMessages.PATIENT_ALREADY_ASSIGNED);
        }
    }

    @Override
    public void removePatient(UUID doctor_uuid, UUID patient_uuid) throws IllegalArgumentException {
        Optional<AppUser> doctor = userService.findById(doctor_uuid);
        Optional<AppUser> patient = userService.findById(patient_uuid);

        doctor.ifPresent(userService::checkDoctorRole);
        patient.ifPresent(userService::checkPatientRole);

        if (doctor.get().getPatients().contains(patient.get())) {
            doctor.ifPresent((d) -> d.getPatients().remove(patient.get()));
            userService.addItem(doctor.get());
            logger.info("Removed patient [" + patient.get().getUsername() + "] from doctor ["
                    + doctor.get().getUsername() + "]");
        } else {
            throw new IllegalArgumentException(ErrorMessages.PATIENT_ISNT_ASSIGNED);
        }
    }
}
