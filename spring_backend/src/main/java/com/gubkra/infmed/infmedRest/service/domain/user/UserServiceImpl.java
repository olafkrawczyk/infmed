package com.gubkra.infmed.infmedRest.service.domain.user;

import com.gubkra.infmed.infmedRest.domain.Address;
import com.gubkra.infmed.infmedRest.domain.AppUser;
import com.gubkra.infmed.infmedRest.domain.Role;
import com.gubkra.infmed.infmedRest.repository.RoleRepository;
import com.gubkra.infmed.infmedRest.repository.AppUserRepository;
import com.gubkra.infmed.infmedRest.service.AbstractRepositoryService;
import com.gubkra.infmed.infmedRest.service.ErrorMessages;
import com.gubkra.infmed.infmedRest.service.domain.address.AddressService;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.EmailExists;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.PeselExists;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.UserExists;
import com.gubkra.infmed.infmedRest.utils.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Olaf on 2018-03-11.
 */

@Service
public class UserServiceImpl extends AbstractRepositoryService<AppUser, UUID> implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AddressService addressService;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, PasswordEncoder passwordEncoder, AddressService addressService) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressService = addressService;
    }

    @Autowired
    private void setRepository(AppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public AppUser addItem(AppUser appUser) {
        return this.repository.save(appUser);
    }

    @Override
    public Optional<AppUser> findById(UUID uuid) {
        return this.repository.findById(uuid);
    }

    @Override
    public AppUser findByUsername(String username) {
        return ((AppUserRepository)this.repository).findByUsername(username);
    }

    @Transactional
    @Override
    public AppUser registerPatient(AppUser appUser) throws EmailExists, UserExists, PeselExists {
        prepareUser(appUser);

        Role role = roleRepository.findByName(SecurityConstants.ROLE_PATIENT);
        appUser.setRoles(Arrays.asList(role));
        logger.info("Saving new patient: " + appUser.getUsername() + " : " + appUser.getEmailAddress());
        return addItem(appUser);
    }

    @Transactional
    @Override
    public AppUser registerDoctor(AppUser appUser) throws EmailExists, UserExists, PeselExists {
        prepareUser(appUser);
        Role role = roleRepository.findByName(SecurityConstants.ROLE_DOCTOR);
        appUser.setRoles(Arrays.asList(role));
        logger.info("Saving new doctor: " + appUser.getUsername() + " : " + appUser.getEmailAddress());
        return addItem(appUser);
    }

    private void prepareUser(AppUser appUser) throws EmailExists, UserExists, PeselExists {
        checkIfEmailExists(appUser);
        checkIfUserExists(appUser);
        checkIfPeselExists(appUser);

        appUser.setUuid(UUID.randomUUID());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        Address address = addressService.addItem(appUser.getAddress());
        appUser.setAddress(address);
    }

    private void checkIfPeselExists(AppUser appUser) throws PeselExists {
        AppUser repositoryAppUser = ((AppUserRepository)this.repository).findByPesel(appUser.getPesel());
        if (repositoryAppUser != null) {
            throw new PeselExists("PESEL already exists: " + appUser.getPesel());
        }
    }

    private void checkIfEmailExists(AppUser appUser) throws EmailExists {
        AppUser repositoryAppUser = ((AppUserRepository)this.repository).findByEmailAddress(appUser.getEmailAddress());
        if (repositoryAppUser != null) {
            throw new EmailExists("Email already exists: " + appUser.getEmailAddress());
        }
    }

    private void checkIfUserExists(AppUser appUser) throws UserExists {
        AppUser repositoryAppUser = ((AppUserRepository)this.repository).findByUsername(appUser.getUsername());
        if (repositoryAppUser != null) {
            throw new UserExists("AppUser already exists: " + appUser.getUsername());
        }
    }

    @org.springframework.transaction.annotation.Transactional
    public Collection<? extends GrantedAuthority> getAuthorities(AppUser user) {
        Collection<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    public void checkPatientRole(AppUser patient) {
        Role patientRole = roleRepository.findByName(SecurityConstants.ROLE_PATIENT);
        if (!patient.getRoles().contains(patientRole)) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_PATIENT_ROLE);
        }
    }

    public void checkDoctorRole(AppUser doctor) {
        Role doctorRole = roleRepository.findByName(SecurityConstants.ROLE_DOCTOR);

        if (!doctor.getRoles().contains(doctorRole)) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_DOCTOR_ROLE);
        }
    }
}
