package com.gubkra.infmed.infmedRest.service.domain.user;

import com.gubkra.infmed.infmedRest.domain.AppUser;
import com.gubkra.infmed.infmedRest.service.CrudService;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.EmailExists;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.UserExists;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */
public interface UserService extends CrudService<AppUser> {
    Optional<AppUser> findById(UUID uuid);
    AppUser findByUsername(String username);
    AppUser registerPatient(AppUser appUser) throws EmailExists, UserExists;
    AppUser registerDoctor(AppUser appUser) throws EmailExists, UserExists;
    Collection<? extends GrantedAuthority> getAuthorities(AppUser user);
    void checkPatientRole(AppUser patient);
    void checkDoctorRole(AppUser doctor);
}
