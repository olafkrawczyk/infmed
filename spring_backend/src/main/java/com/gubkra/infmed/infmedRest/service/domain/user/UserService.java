package com.gubkra.infmed.infmedRest.service.domain.user;

import com.gubkra.infmed.infmedRest.domain.User;
import com.gubkra.infmed.infmedRest.domain.dto.UserRegisterDTO;
import com.gubkra.infmed.infmedRest.service.CrudService;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.EmailExists;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.UserExists;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */
public interface UserService extends CrudService<User> {
    Optional<User> findById(UUID uuid);
    User registerPatient(User user) throws EmailExists, UserExists;
    User registerDoctor(User user) throws EmailExists, UserExists;
}
