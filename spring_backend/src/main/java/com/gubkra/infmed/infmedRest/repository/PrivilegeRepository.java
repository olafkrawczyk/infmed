package com.gubkra.infmed.infmedRest.repository;

import com.gubkra.infmed.infmedRest.domain.Privilege;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Olaf on 2018-03-18.
 */

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {

    Privilege findByName(String name);
}
