package com.gubkra.infmed.infmedRest.utils;

import com.gubkra.infmed.infmedRest.domain.*;
import com.gubkra.infmed.infmedRest.repository.*;
import com.gubkra.infmed.infmedRest.service.domain.address.AddressService;
import com.gubkra.infmed.infmedRest.service.domain.user.UserService;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.EmailExists;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.UserExists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Olaf on 2018-03-11.
 */

@Profile("dev")
@Component
public class DevDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DevDataLoader.class);

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    private boolean refreshed = false;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private TemperatureExaminationRepository temperatureExaminationRepository;
    @Autowired
    private HeartRateExaminationRepository heartRateExaminationRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        logger.info("Data loader invoked...");
        if (refreshed)
            return;
        loadUsers();
        refreshed = true;
    }

    private void loadUsers() {

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

        AppUser patient = new AppUser();
        patient.setEmailAddress("patient@example.com");
        patient.setPassword("password12345");
        patient.setUsername("patient1");
        patient.setBirthDate(LocalDate.of(1994, 4, 12));
        patient.setPhoneNumber("634 234 345");
        patient.setName("Kazik");
        patient.setSurname("Nowak");
        patient.setPesel("93853712563");

        AppUser patient2 = new AppUser();
        patient2.setEmailAddress("patient2@example.com");
        patient2.setPassword("password12345");
        patient2.setUsername("patient2");
        patient2.setBirthDate(LocalDate.of(1994, 4, 12));
        patient2.setPhoneNumber("634 234 345");
        patient2.setName("Kazik");
        patient2.setSurname("Nowak");
        patient2.setPesel("56231245684");

        AppUser appUser = new AppUser();
        appUser.setEmailAddress("doctor@example.com");
        appUser.setPassword("password12345");
        appUser.setUsername("doctor1");
        appUser.setBirthDate(LocalDate.of(1994, 4, 12));
        appUser.setPhoneNumber("876 234 543");
        appUser.setName("Jan");
        appUser.setSurname("Kowalski");
        appUser.setPesel("94041243567");

        appUser.setAddress(address1);
        patient.setAddress(address2);
        patient2.setAddress(address2);

        assignPatientToDoctorAndSave(patient, patient2, appUser);

        for (int i = 0; i <= 100; i++) {
            addExaminations(patient2);
            addExaminations(patient);
        }
    }

    private void assignPatientToDoctorAndSave(AppUser patient, AppUser patient2, AppUser appUser) {
        try {
            userService.registerDoctor(appUser);
            userService.registerPatient(patient);
            userService.registerPatient(patient2);
            appUser.setPatients(Arrays.asList(patient, patient2));
            appUserRepository.save(appUser);
        } catch (EmailExists | UserExists emailExists) {
            emailExists.printStackTrace();
        }
    }

    private void addExaminations(AppUser patient) {
        int randomInt = ThreadLocalRandom.current().nextInt(60, 200);
        int randomDay = ThreadLocalRandom.current().nextInt(1, 28);
        int randomMonth = ThreadLocalRandom.current().nextInt(1, 12);
        double randomDouble = ThreadLocalRandom.current().nextDouble(32.0, 49.0);
        randomDouble = Math.round(randomDouble);

        TemperatureExamination temp1 = new TemperatureExamination();
        temp1.setValue(randomDouble);
        temp1.setPatient(patient);
        temp1.setDate(LocalDate.of(2018, randomMonth, randomDay));
        temperatureExaminationRepository.save(temp1);

        HeartRateExaminaiton hr1 = new HeartRateExaminaiton();
        hr1.setValue(randomInt);
        hr1.setDate(LocalDate.of(2018, randomMonth, randomDay));
        hr1.setPatient(patient);
        heartRateExaminationRepository.save(hr1);
    }
}
