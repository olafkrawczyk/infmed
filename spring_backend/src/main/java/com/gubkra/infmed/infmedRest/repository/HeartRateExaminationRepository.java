package com.gubkra.infmed.infmedRest.repository;

import com.gubkra.infmed.infmedRest.domain.HeartRateExaminaiton;

import javax.transaction.Transactional;

@Transactional
public interface HeartRateExaminationRepository extends MedicalExaminationRepository<HeartRateExaminaiton> {
}
