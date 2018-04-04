package com.gubkra.infmed.infmedRest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gubkra.infmed.infmedRest.service.doctor.DoctorErrorMessages;
import com.gubkra.infmed.infmedRest.service.doctor.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/doctor")
public class DoctorController {
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    private DoctorService doctorService;


    @Secured("ROLE_DOCTOR")
    @PostMapping(value = "/patient")
    public ResponseEntity registerPatient(@RequestBody ObjectNode request) {
        String patient_key = "patient_uuid";
        String doctor_key = "doctor_uuid";

        if (!request.hasNonNull(doctor_key) || !request.hasNonNull(patient_key)) {
            return ResponseEntity.badRequest().body("Invalid request. doctor_uuid or patient_uuid is empty");
        }

        try {
            UUID doctor_uuid = UUID.fromString(request.get(doctor_key).asText());
            UUID patient_uuid = UUID.fromString(request.get(patient_key).asText());
            doctorService.registerPatient(doctor_uuid, patient_uuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Patient successfully registered.");
    }
}
