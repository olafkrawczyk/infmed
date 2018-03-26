package com.gubkra.infmed.infmedRest.service.domain.user;

import com.gubkra.infmed.infmedRest.domain.Role;
import com.gubkra.infmed.infmedRest.domain.User;
import com.gubkra.infmed.infmedRest.domain.dto.UserRegisterDTO;
import com.gubkra.infmed.infmedRest.repository.RoleRepository;
import com.gubkra.infmed.infmedRest.repository.UserRepository;
import com.gubkra.infmed.infmedRest.service.AbstractRepositoryService;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.EmailExists;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.UserExists;
import com.gubkra.infmed.infmedRest.utils.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */

@Service
public class UserServiceImpl extends AbstractRepositoryService<User, UUID> implements UserService {
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User addItem(User user) {
        return this.repository.save(user);
    }

    @Override
    public Optional<User> findById(UUID uuid) {
        return this.repository.findById(uuid);
    }

    @Override
    public User registerPatient(User user) throws EmailExists, UserExists {
        prepareUser(user);

        Role role = roleRepository.findByName(SecurityConstants.ROLE_PATIENT);
        user.setRoles(Arrays.asList(role));

        return addItem(user);
    }

    @Override
    public User registerDoctor(User user) throws EmailExists, UserExists {
        prepareUser(user);
        Role role = roleRepository.findByName(SecurityConstants.ROLE_DOCTOR);
        user.setRoles(Arrays.asList(role));

        return addItem(user);
    }

    private void prepareUser(User user) throws EmailExists, UserExists {
        checkIfEmailExists(user);
        checkIfUserExists(user);

        user.setUuid(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void checkIfEmailExists(User user) throws EmailExists {
        User repositoryUser = this.repository.findByEmailAddress(user.getEmailAddress());
        if (repositoryUser != null) {
            throw new EmailExists("Email already exists: " + user.getEmailAddress());
        }
    }

    private void checkIfUserExists(User user) throws UserExists {
        User repositoryUser = this.repository.findByUsername(user.getUsername());
        if (repositoryUser != null) {
            throw new UserExists("User already exists: " + user.getUsername());
        }
    }
}
