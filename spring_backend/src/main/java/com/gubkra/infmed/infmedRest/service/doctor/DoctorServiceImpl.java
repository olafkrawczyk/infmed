package com.gubkra.infmed.infmedRest.service.doctor;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gubkra.infmed.infmedRest.domain.AppUser;
import com.gubkra.infmed.infmedRest.domain.Role;
import com.gubkra.infmed.infmedRest.repository.RoleRepository;
import com.gubkra.infmed.infmedRest.service.domain.user.UserService;
import com.gubkra.infmed.infmedRest.utils.SecurityConstants;
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

    @Override
    public void registerPatient(UUID doctor_uuid, UUID patient_uuid) throws IllegalArgumentException {
        Optional<AppUser> doctor = userService.findById(doctor_uuid);
        Optional<AppUser> patient = userService.findById(patient_uuid);

        checkUsersAndRoles(doctor, patient);

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

        checkUsersAndRoles(doctor, patient);

        if (doctor.get().getPatients().contains(patient.get())) {
            doctor.ifPresent((d) -> d.getPatients().remove(patient.get()));
            userService.addItem(doctor.get());
            logger.info("Removed patient [" + patient.get().getUsername() + "] from doctor ["
                    + doctor.get().getUsername() + "]");
        } else {
            throw new IllegalArgumentException(DoctorErrorMessages.PATIENT_ISNT_ASSIGNED);
        }
    }

    private void checkUsersAndRoles(Optional<AppUser> doctor, Optional<AppUser> patient) {
        Role doctorRole = roleRepository.findByName(SecurityConstants.ROLE_DOCTOR);
        Role patientRole = roleRepository.findByName(SecurityConstants.ROLE_PATIENT);
        if (!doctor.isPresent() || !doctor.get().getRoles().contains(doctorRole)) {
            throw new IllegalArgumentException(DoctorErrorMessages.INVALID_DOCTOR_ID);
        }
        if (!patient.isPresent() || !patient.get().getRoles().contains(patientRole)) {
            throw new IllegalArgumentException(DoctorErrorMessages.INVALID_PATIENT_ID);
        }
    }
}
