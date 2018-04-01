package com.gubkra.infmed.infmedRest.utils;

import com.gubkra.infmed.infmedRest.domain.Address;
import com.gubkra.infmed.infmedRest.domain.AppUser;
import com.gubkra.infmed.infmedRest.domain.Privilege;
import com.gubkra.infmed.infmedRest.domain.Role;
import com.gubkra.infmed.infmedRest.repository.PrivilegeRepository;
import com.gubkra.infmed.infmedRest.repository.RoleRepository;
import com.gubkra.infmed.infmedRest.service.domain.address.AddressService;
import com.gubkra.infmed.infmedRest.service.domain.user.UserService;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.EmailExists;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.UserExists;
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
        AppUser patient = new AppUser();
        patient.setEmailAddress("patient@example.com");
        patient.setPassword("password12345");
        patient.setUsername("patient1");
        patient.setBirthDate(LocalDate.of(1994, 4, 12));
        patient.setPhoneNumber("634 234 345");
        patient.setName("Kazik");
        patient.setSurname("Nowak");
        patient.setPesel("93853712563");

        AppUser appUser = new AppUser();
        appUser.setEmailAddress("doctor@example.com");
        appUser.setPassword("password12345");
        appUser.setUsername("doctor1");
        appUser.setBirthDate(LocalDate.of(1994, 4, 12));
        appUser.setPhoneNumber("876 234 543");
        appUser.setName("Jan");
        appUser.setSurname("Kowalski");
        appUser.setPesel("94041243567");

        addressService.findById((long) 1).ifPresent(appUser::setAddress);
        addressService.findById((long) 2).ifPresent(patient::setAddress);

        try {
            userService.registerDoctor(appUser);
            userService.registerPatient(patient);
        } catch (EmailExists | UserExists emailExists) {
            emailExists.printStackTrace();
        }
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
