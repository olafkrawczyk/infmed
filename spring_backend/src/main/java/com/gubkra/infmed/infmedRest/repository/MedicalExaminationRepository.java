package com.gubkra.infmed.infmedRest.repository;

import com.gubkra.infmed.infmedRest.domain.MedicalExamination;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

@NoRepositoryBean
public interface MedicalExaminationRepository<T extends MedicalExamination> extends CrudRepository<T, Long>{
    public Collection<T> findByPatient_Username(String username);
}
