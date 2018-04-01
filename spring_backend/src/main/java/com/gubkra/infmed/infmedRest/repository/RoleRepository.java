package com.gubkra.infmed.infmedRest.repository;

import com.gubkra.infmed.infmedRest.domain.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Olaf on 2018-03-18.
 */

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
