package com.gubkra.infmed.infmedRest.repository;

import com.gubkra.infmed.infmedRest.domain.AppUser;
import com.gubkra.infmed.infmedRest.domain.MedicalExamination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

@NoRepositoryBean
public interface MedicalExaminationRepository<T extends MedicalExamination> extends PagingAndSortingRepository<T, Long> {
    Collection<T> findByPatient_Username(String username);
    Page<T> findAllByPatient(AppUser patient, Pageable pageable);
}
