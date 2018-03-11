package com.gubkra.infmed.infmedRest.controller;

import com.gubkra.infmed.infmedRest.domain.Patient;
import com.gubkra.infmed.infmedRest.service.domain.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Olaf on 2018-03-11.
 */
@RestController
@RequestMapping(value = "/patient")
public class PatientController {
    @Autowired
    PatientService patientService;

    @GetMapping(value = "")
    public Iterable<Patient> getAll() {
        return patientService.findAll();
    }
}
