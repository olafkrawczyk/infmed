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
import com.gubkra.infmed.infmedRest.utils.ResponseMessageWrapper;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;


@RestController
@RequestMapping(value = "/patient")
public class PatientController {
    private final TemperatureExaminationRepository temperatureExaminationRepository;

    private final HeartRateExaminationRepository heartRateExaminationRepository;

    private final AppUserRepository appUserRepository;

    private final ExaminationService examinationService;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public PatientController(TemperatureExaminationRepository temperatureExaminationRepository, HeartRateExaminationRepository heartRateExaminationRepository, AppUserRepository appUserRepository, ExaminationService examinationService) {
        this.temperatureExaminationRepository = temperatureExaminationRepository;
        this.heartRateExaminationRepository = heartRateExaminationRepository;
        this.appUserRepository = appUserRepository;
        this.examinationService = examinationService;
    }

    @Transactional
    @GetMapping(value = "/examination/{patientUsername:.+}/temperature")
    public Page<TemperatureExaminationDTO> listTemperatureExamination(Pageable pageable, @PathVariable("patientUsername") String username,
                                                                      @RequestParam(value = "startDate", required = false) String startDate,
                                                                      @RequestParam(value = "endDate", required = false) String endDate) {
        AppUser patient = this.appUserRepository.findByUsername(username);

        Page<TemperatureExamination> pg;

        if (startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            pg = temperatureExaminationRepository.findAllByPatientAndDateBetween(patient, start, end, pageable);
        } else {
            pg = temperatureExaminationRepository.findAllByPatient(patient, pageable);
        }

        return pg.map((te) -> modelMapper.map(te, TemperatureExaminationDTO.class));
    }

    @Transactional
    @GetMapping(value = "/examination/{patientUsername:.+}/heart-rate")
    public Page<HeartRateExaminationDTO> listHeartRateExamination(Pageable pageable, @PathVariable("patientUsername") String username,
                                                                  @RequestParam(value = "startDate", required = false) String startDate,
                                                                  @RequestParam(value = "endDate", required = false) String endDate) {
        AppUser patient = this.appUserRepository.findByUsername(username);

        Page<HeartRateExaminaiton> pg;

        if (startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            pg = heartRateExaminationRepository.findAllByPatientAndDateBetween(patient, start, end, pageable);
        } else {
            pg = heartRateExaminationRepository.findAllByPatient(patient, pageable);
        }

        return pg.map((te) -> modelMapper.map(te, HeartRateExaminationDTO.class));
    }

    @Secured("ROLE_PATIENT")
    @PostMapping(value = "/examination/heart-rate")
    public ResponseEntity saveHeartRateExamination(Principal principal, @RequestBody HeartRateExaminationDTO request) {
        HeartRateExaminaiton heartRate = modelMapper.map(request, HeartRateExaminaiton.class);

        if (request.getValue() == null || request.getRawData() == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageWrapper("Could not find value or rawData field in request"));
        }

        try {
            examinationService.saveHeartRateExamination(principal.getName(), heartRate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseMessageWrapper(e.getMessage()));
        }

        return ResponseEntity.ok(new ResponseMessageWrapper("Examination saved"));
    }

    @ApiOperation(value = "Add temperature examination", notes = "Gets { value: 36.6 }")
    @Secured("ROLE_PATIENT")
    @PostMapping(value = "/examination/temperature")
    public ResponseEntity saveTemperatureExamination(Principal principal, @RequestBody ObjectNode request) {
        Double temperature;

        if (!request.hasNonNull("value")) {
            return ResponseEntity.badRequest().body(new ResponseMessageWrapper("Could not find value field in request"));
        }

        try {
            temperature = Double.valueOf(request.get("value").asText());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseMessageWrapper("Invalid value: " + request.get("value").asText() + ". Could not convert to double"));
        }

        try {
            examinationService.saveTemperatureExamination(principal.getName(), temperature);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseMessageWrapper(e.getMessage()));
        }

        return ResponseEntity.ok(new ResponseMessageWrapper("Examination saved"));
    }
}
