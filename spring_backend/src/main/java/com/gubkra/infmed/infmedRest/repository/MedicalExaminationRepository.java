package com.gubkra.infmed.infmedRest.repository;

import com.gubkra.infmed.infmedRest.domain.AppUser;
import com.gubkra.infmed.infmedRest.domain.MedicalExamination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@NoRepositoryBean
public interface MedicalExaminationRepository<T extends MedicalExamination> extends PagingAndSortingRepository<T, Long> {
    @Transactional
    Collection<T> findByPatient_Username(String username);
    @Transactional
    Page<T> findAllByPatient(AppUser patient, Pageable pageable);
    Page<T> findAllByPatientAndDateBetween(AppUser patient, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
