package com.gubkra.infmed.infmedRest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gubkra.infmed.infmedRest.domain.AppUser;
import com.gubkra.infmed.infmedRest.domain.HeartRateExaminaiton;
import com.gubkra.infmed.infmedRest.domain.dto.AppUserDTO;
import com.gubkra.infmed.infmedRest.domain.dto.HeartRateExaminationDTO;
import com.gubkra.infmed.infmedRest.repository.AppUserRepository;
import com.gubkra.infmed.infmedRest.service.doctor.DoctorService;
import com.gubkra.infmed.infmedRest.service.examination.ExaminationService;
import com.gubkra.infmed.infmedRest.utils.ResponseMessageWrapper;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private AppUserRepository userRepository;

    @Autowired
    public DoctorController(DoctorService doctorService, ExaminationService examinationService, AppUserRepository userRepository) {
        this.doctorService = doctorService;
        this.examinationService = examinationService;
        this.userRepository = userRepository;
    }


    @ApiOperation(value = "Assign patient to doctor", notes = "{ doctor_uuid : ..., patient_uuid : ...}")
    @PostMapping(value = "/patient")
    public ResponseEntity registerPatient(@RequestBody ObjectNode request) {

        if (!request.hasNonNull(doctor_key) || !request.hasNonNull(patient_key)) {
            return ResponseEntity.badRequest().body(new ResponseMessageWrapper("Invalid request. doctor_uuid or patient_uuid is empty"));
        }

        try {
            UUID doctor_uuid = getUUID(request, doctor_key);
            UUID patient_uuid = getUUID(request, patient_key);
            doctorService.registerPatient(doctor_uuid, patient_uuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseMessageWrapper(e.getMessage()));
        }

        return ResponseEntity.ok(new ResponseMessageWrapper("Patient successfully registered."));
    }

    @GetMapping(value="/patients/findByPESEL/{pesel}")
    public ResponseEntity findByPesel(Principal principal, @PathVariable("pesel") String pesel){
        AppUser patient = this.userRepository.findByPesel(pesel);
        if (patient != null) {
            return ResponseEntity.ok(this.modelMapper.map(patient, AppUserDTO.class));
        }

        return ResponseEntity.status(404).body(new ResponseMessageWrapper("Could not find patient with given PESEL"));
    }

    @GetMapping(value="/patients")
    public ResponseEntity getPatients(Principal principal) {
        AppUser doctor = this.userRepository.findByUsername(principal.getName());
        List<AppUserDTO> patients = doctor.getPatients().stream().map(x -> this.modelMapper.map(x, AppUserDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(patients);
    }

    @GetMapping(value="/patients/{patientUsername:.+}")
    public ResponseEntity getPatient(@PathVariable("patientUsername") String username) {
        //TODO : Check if requested patient is registered to doctor
        AppUser patient = userRepository.findByUsername(username);
        if(patient != null) {
            return ResponseEntity.ok(modelMapper.map(patient, AppUserDTO.class));
        }
        return ResponseEntity.status(404).body(new ResponseMessageWrapper("Could not find patient: " + username));
    }

    @ApiOperation(value = "Remove patient from doctor", notes = "{ doctor_uuid : ..., patient_uuid : ...}")
    @DeleteMapping(value = "/patient")
    public ResponseEntity removePatient(@RequestBody ObjectNode request) {

        if (!request.hasNonNull(doctor_key) || !request.hasNonNull(patient_key)) {
            return ResponseEntity.badRequest().body(new ResponseMessageWrapper("Invalid request. doctor_uuid or patient_uuid is empty"));
        }

        try {
            UUID doctor_uuid = getUUID(request, doctor_key);
            UUID patient_uuid = getUUID(request, patient_key);
            doctorService.removePatient(doctor_uuid, patient_uuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseMessageWrapper(e.getMessage()));
        }

        return ResponseEntity.ok(new ResponseMessageWrapper("Patient successfully removed from doctor."));
    }

    @PostMapping(value = "/examination/{patientUsername:.+}/temperature")
    public ResponseEntity saveTemperatureExamination(@PathVariable String patientUsername, @RequestBody ObjectNode request) {
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
            examinationService.saveTemperatureExamination(patientUsername, temperature);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseMessageWrapper(e.getMessage()));
        }

        return ResponseEntity.ok(new ResponseMessageWrapper("Examination saved"));
    }

    @PostMapping(value = "/examination/{patientUsername:.+}/heart-rate")
    public ResponseEntity saveHeartRateExamination(@PathVariable String patientUsername, @RequestBody HeartRateExaminationDTO request) {
        HeartRateExaminaiton heartRate = modelMapper.map(request, HeartRateExaminaiton.class);

        if (request.getValue() == null || request.getRawData() == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageWrapper("Could not find value or rawData field in request"));
        }

        try {
            examinationService.saveHeartRateExamination(patientUsername, heartRate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseMessageWrapper(e.getMessage()));
        }

        return ResponseEntity.ok(new ResponseMessageWrapper("Examination saved"));
    }

    private UUID getUUID(ObjectNode request, String key) {
        return UUID.fromString(request.get(key).asText());
    }

}
