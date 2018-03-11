package com.gubkra.infmed.infmedRest.repository;

import com.gubkra.infmed.infmedRest.domain.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */

public interface PatientRepository extends CrudRepository<Patient, UUID> {

}
