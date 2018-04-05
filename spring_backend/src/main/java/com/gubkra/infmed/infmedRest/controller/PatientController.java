package com.gubkra.infmed.infmedRest.controller;

import com.gubkra.infmed.infmedRest.domain.TemperatureExamination;
import com.gubkra.infmed.infmedRest.domain.dto.TemperatureExaminationDTO;
import com.gubkra.infmed.infmedRest.repository.TemperatureExaminationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {
    @Autowired
    private TemperatureExaminationRepository temperatureExaminationRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping(value = "/examination/{patientUsername}/temperature")
    public Page<TemperatureExaminationDTO> listTemperatureExamination(Pageable pageable, @PathVariable("patientUsername") String username){
        Page<TemperatureExamination> pg = temperatureExaminationRepository.findAll(pageable);
        return pg.map((te) -> modelMapper.map(te, TemperatureExaminationDTO.class));
    }
}
