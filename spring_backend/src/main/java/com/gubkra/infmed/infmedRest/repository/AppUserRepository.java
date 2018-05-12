package com.gubkra.infmed.infmedRest.repository;

import com.gubkra.infmed.infmedRest.domain.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */

public interface AppUserRepository extends CrudRepository<AppUser, UUID> {
    AppUser findByEmailAddress(String emailAddress);
    AppUser findByUsername(String username);
    AppUser findByPesel(String pesel);
}
