package com.gubkra.infmed.infmedRest.service.domain.patient;

import com.gubkra.infmed.infmedRest.domain.Patient;
import com.gubkra.infmed.infmedRest.repository.PatientRepository;
import com.gubkra.infmed.infmedRest.service.AbstractRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */

@Service
public class PatientServiceImpl extends AbstractRepositoryService<Patient, UUID> implements PatientService {
    @Autowired
    private void setRepository(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Patient addItem(Patient patient) {
        UUID uuid = UUID.randomUUID();
        patient.setUuid(uuid);
        return this.repository.save(patient);
    }

    @Override
    public Optional<Patient> findById(UUID uuid) {
        return this.repository.findById(uuid);
    }
}
