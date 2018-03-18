package com.gubkra.infmed.infmedRest.service.domain.user;

import com.gubkra.infmed.infmedRest.domain.User;
import com.gubkra.infmed.infmedRest.service.CrudService;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */
public interface UserService extends CrudService<User> {
    Optional<User> findById(UUID uuid);
}
