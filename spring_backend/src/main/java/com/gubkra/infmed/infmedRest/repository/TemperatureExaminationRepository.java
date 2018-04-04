package com.gubkra.infmed.infmedRest.repository;

import com.gubkra.infmed.infmedRest.domain.TemperatureExamination;

import javax.transaction.Transactional;

@Transactional
public interface TemperatureExaminationRepository extends MedicalExaminationRepository<TemperatureExamination> {
}
