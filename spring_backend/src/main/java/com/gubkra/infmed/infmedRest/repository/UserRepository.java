package com.gubkra.infmed.infmedRest.repository;

import com.gubkra.infmed.infmedRest.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */

public interface UserRepository extends CrudRepository<User, UUID> {

}
