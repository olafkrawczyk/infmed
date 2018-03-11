package com.gubkra.infmed.infmedRest.service.domain.patient;

import com.gubkra.infmed.infmedRest.domain.Patient;
import com.gubkra.infmed.infmedRest.service.CrudService;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */
public interface PatientService extends CrudService<Patient> {
    Optional<Patient> findById(UUID uuid);
}
