package com.gubkra.infmed.infmedRest.utils;

import com.gubkra.infmed.infmedRest.domain.Address;
import com.gubkra.infmed.infmedRest.domain.User;
import com.gubkra.infmed.infmedRest.service.domain.address.AddressService;
import com.gubkra.infmed.infmedRest.service.domain.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Created by Olaf on 2018-03-11.
 */

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        logger.info("Data loader invoked...");

        loadAddresses();
        loadUsers();
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

        addressService.findById((long)1).ifPresent(user::setAddress);

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
}
