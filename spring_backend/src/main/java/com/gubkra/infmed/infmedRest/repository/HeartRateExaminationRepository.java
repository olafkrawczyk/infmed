package com.gubkra.infmed.infmedRest.repository;

import com.gubkra.infmed.infmedRest.domain.HeartRateExaminaiton;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

@Transactional
public interface HeartRateExaminationRepository extends MedicalExaminationRepository<HeartRateExaminaiton> {
}
