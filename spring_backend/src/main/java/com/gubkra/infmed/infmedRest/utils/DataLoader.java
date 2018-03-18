package com.gubkra.infmed.infmedRest.utils;

import com.gubkra.infmed.infmedRest.domain.Address;
import com.gubkra.infmed.infmedRest.domain.Privilege;
import com.gubkra.infmed.infmedRest.domain.Role;
import com.gubkra.infmed.infmedRest.domain.User;
import com.gubkra.infmed.infmedRest.repository.PrivilegeRepository;
import com.gubkra.infmed.infmedRest.repository.RoleRepository;
import com.gubkra.infmed.infmedRest.service.domain.address.AddressService;
import com.gubkra.infmed.infmedRest.service.domain.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Olaf on 2018-03-11.
 */

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    private boolean refreshed = false;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        logger.info("Data loader invoked...");

        if (refreshed)
            return;

        Privilege addPatient = createPrivilegeIfNotExists(SecurityConstants.ADD_PATIENT_P);
        Privilege deletePatient = createPrivilegeIfNotExists(SecurityConstants.DELETE_PATIENT_P);
        Privilege deleteMedicalRecord = createPrivilegeIfNotExists(SecurityConstants.DELETE_RECORD_P);
        Privilege saveMedicalRecord = createPrivilegeIfNotExists(SecurityConstants.SAVE_RECORD_P);

        Role doctorRole = createRoleIfNotExists(SecurityConstants.ROLE_DOCTOR,
                Arrays.asList(addPatient, deletePatient, deleteMedicalRecord, saveMedicalRecord));
        Role patientRole = createRoleIfNotExists(SecurityConstants.ROLE_PATIENT, Arrays.asList(saveMedicalRecord));

        loadAddresses();

        loadUsers();

        refreshed = true;

    }

    private void loadUsers() {
        User user = new User();
        user.setEmailAddress("user@example.com");
        user.setPassword("password12345");
        user.setUsername("user1");
        user.setBirthDate(LocalDate.of(1994, 4, 12));
        user.setPhoneNumber("634 234 345");
        user.setName("Jan");
        user.setSurname("Kowalski");
        user.setPesel("94041243567");

        Role doctorRole = roleRepository.findByName(SecurityConstants.ROLE_DOCTOR);
        user.setRoles(Arrays.asList(doctorRole));

        addressService.findById((long) 1).ifPresent(user::setAddress);

        userService.addItem(user);
    }

    private void loadAddresses() {
        Address address1 = new Address();
        address1.setCity("Wroclaw");
        address1.setStreet("Sezamkowa");
        address1.setHouseNumber("66");
        address1.setPostalCode("50-363");

        Address address2 = new Address();
        address2.setCity("Poznań");
        address2.setStreet("Długa");
        address2.setHouseNumber("33");
        address2.setPostalCode("30-402");
        address2.setApartmentNumber("2");

        this.addressService.addItem(address1);
        this.addressService.addItem(address2);
    }

    private Privilege createPrivilegeIfNotExists(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }

    private Role createRoleIfNotExists(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            role = roleRepository.save(role);
        }
        return role;
    }

}
