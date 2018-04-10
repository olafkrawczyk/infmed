package com.gubkra.infmed.infmedRest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gubkra.infmed.infmedRest.domain.HeartRateExaminaiton;
import com.gubkra.infmed.infmedRest.domain.dto.HeartRateExaminationDTO;
import com.gubkra.infmed.infmedRest.service.doctor.DoctorService;
import com.gubkra.infmed.infmedRest.service.examination.ExaminationService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
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

    private final DoctorService doctorService;

    private final ExaminationService examinationService;

    private String patient_key = "patient_uuid";
    private String doctor_key = "doctor_uuid";

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public DoctorController(DoctorService doctorService, ExaminationService examinationService) {
        this.doctorService = doctorService;
        this.examinationService = examinationService;
    }


    @ApiOperation(value = "Assign patient to doctor", notes = "{ doctor_uuid : ..., patient_uuid : ...}")
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


    @ApiOperation(value = "Remove patient from doctor", notes = "{ doctor_uuid : ..., patient_uuid : ...}")
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
    public ResponseEntity saveHeartRateExamination(@PathVariable String patientUsername, @RequestBody HeartRateExaminationDTO request) {
        HeartRateExaminaiton heartRate = modelMapper.map(request, HeartRateExaminaiton.class);

        if (request.getValue() == null || request.getRawData() == null) {
            return ResponseEntity.badRequest().body("Could not find value or rawData field in request");
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
