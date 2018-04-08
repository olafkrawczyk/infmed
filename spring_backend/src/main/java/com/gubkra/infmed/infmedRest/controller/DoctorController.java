package com.gubkra.infmed.infmedRest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gubkra.infmed.infmedRest.service.doctor.DoctorService;
import com.gubkra.infmed.infmedRest.service.examination.ExaminationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Secured("ROLE_DOCTOR")
@RestController
@RequestMapping(value = "/doctor")
public class DoctorController {
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ExaminationService examinationService;

    private String patient_key = "patient_uuid";
    private String doctor_key = "doctor_uuid";

    @PostMapping(value = "/patient")
    public ResponseEntity registerPatient(@RequestBody ObjectNode request) {

        if (!request.hasNonNull(doctor_key) || !request.hasNonNull(patient_key)) {
            return ResponseEntity.badRequest().body("Invalid request. doctor_uuid or patient_uuid is empty");
        }

        try {
            UUID doctor_uuid = getUUID(request, doctor_key);
            UUID patient_uuid = getUUID(request, patient_key);
            doctorService.registerPatient(doctor_uuid, patient_uuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Patient successfully registered.");
    }


    @DeleteMapping(value = "/patient")
    public ResponseEntity removePatient(@RequestBody ObjectNode request) {

        if (!request.hasNonNull(doctor_key) || !request.hasNonNull(patient_key)) {
            return ResponseEntity.badRequest().body("Invalid request. doctor_uuid or patient_uuid is empty");
        }

        try {
            UUID doctor_uuid = getUUID(request, doctor_key);
            UUID patient_uuid = getUUID(request, patient_key);
            doctorService.removePatient(doctor_uuid, patient_uuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Patient successfully removed from doctor.");
    }

    @PostMapping(value = "/examination/{patientUsername}/temperature")
    public ResponseEntity saveTemperatureExamination(@PathVariable String patientUsername, @RequestBody ObjectNode request) {
        Double temperature;
        
        if (!request.hasNonNull("value")) {
            return ResponseEntity.badRequest().body("Could not find value field in request");
        }

        try {
            temperature = Double.valueOf(request.get("value").asText());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid value: " + request.get("value").asText() + ". Could not convert to double");
        }

        try {
            examinationService.saveTemperatureExamination(patientUsername, temperature);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Examination saved");
    }

    @PostMapping(value = "/examination/{patientUsername}/heart-rate")
    public ResponseEntity saveHeartRateExamination(@PathVariable String patientUsername, @RequestBody ObjectNode request) {
        Integer heartRate;

        if (!request.hasNonNull("value")) {
            return ResponseEntity.badRequest().body("Could not find value field in request");
        }

        try {
            heartRate = Integer.valueOf(request.get("value").asText());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid value: " + request.get("value").asText() + ". Could not convert to Integer");
        }

        try {
            examinationService.saveHeartRateExamination(patientUsername, heartRate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Examination saved");
    }

    private UUID getUUID(ObjectNode request, String key) {
        return UUID.fromString(request.get(key).asText());
    }

}
