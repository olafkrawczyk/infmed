package com.gubkra.infmed.infmedRest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gubkra.infmed.infmedRest.domain.AppUser;
import com.gubkra.infmed.infmedRest.domain.HeartRateExaminaiton;
import com.gubkra.infmed.infmedRest.domain.TemperatureExamination;
import com.gubkra.infmed.infmedRest.domain.dto.HeartRateExaminationDTO;
import com.gubkra.infmed.infmedRest.domain.dto.TemperatureExaminationDTO;
import com.gubkra.infmed.infmedRest.repository.AppUserRepository;
import com.gubkra.infmed.infmedRest.repository.HeartRateExaminationRepository;
import com.gubkra.infmed.infmedRest.repository.TemperatureExaminationRepository;
import com.gubkra.infmed.infmedRest.service.examination.ExaminationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping(value = "/patient")
public class PatientController {
    @Autowired
    private TemperatureExaminationRepository temperatureExaminationRepository;

    @Autowired
    private HeartRateExaminationRepository heartRateExaminationRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ExaminationService examinationService;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping(value = "/examination/{patientUsername}/temperature")
    public Page<TemperatureExaminationDTO> listTemperatureExamination(Pageable pageable, @PathVariable("patientUsername") String username){
        AppUser patient = this.appUserRepository.findByUsername(username);
        Page<TemperatureExamination> pg = temperatureExaminationRepository.findAllByPatient(patient, pageable);
        return pg.map((te) -> modelMapper.map(te, TemperatureExaminationDTO.class));
    }

    @GetMapping(value = "/examination/{patientUsername}/heart-rate")
    public Page<HeartRateExaminationDTO> listHeartRateExamination(Pageable pageable, @PathVariable("patientUsername") String username){
        AppUser patient = this.appUserRepository.findByUsername(username);
        Page<HeartRateExaminaiton> pg = heartRateExaminationRepository.findAllByPatient(patient, pageable);
        return pg.map((te) -> modelMapper.map(te, HeartRateExaminationDTO.class));
    }

    @Secured("ROLE_PATIENT")
    @PostMapping(value = "/examination/heart-rate")
    public ResponseEntity saveHeartRateExamination(Principal principal, @RequestBody ObjectNode request) {
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
            examinationService.saveHeartRateExamination(principal.getName(), heartRate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Examination saved");
    }

    @Secured("ROLE_PATIENT")
    @PostMapping(value = "/examination/temperature")
    public ResponseEntity saveTemperatureExamination(Principal principal, @RequestBody ObjectNode request) {
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
            examinationService.saveTemperatureExamination(principal.getName(), temperature);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Examination saved");
    }
}
