package com.gubkra.infmed.infmedRest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Streams;
import com.gubkra.infmed.infmedRest.domain.Address;
import com.gubkra.infmed.infmedRest.domain.AppUser;
import com.gubkra.infmed.infmedRest.domain.dto.AppUserDTO;
import com.gubkra.infmed.infmedRest.domain.dto.AppUserRegisterDTO;
import com.gubkra.infmed.infmedRest.service.domain.address.AddressService;
import com.gubkra.infmed.infmedRest.service.domain.user.UserService;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.EmailExists;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.PeselExists;
import com.gubkra.infmed.infmedRest.service.domain.user.exceptions.UserExists;
import com.gubkra.infmed.infmedRest.utils.ResponseMessageWrapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olaf on 2018-03-11.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    private final AddressService addressService;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }


    @GetMapping(value = "/all")
    public List<AppUserDTO> getAll() {
        return Streams.stream(userService.findAll()).map(x -> modelMapper.map(x, AppUserDTO.class)).collect(Collectors.toList());
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity getUserByUsername(Principal principal, @PathVariable("username") String username) {
        logger.info(principal.getName());
        AppUser user = userService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(modelMapper.map(user, AppUserDTO.class));
        } else {
            return ResponseEntity.status(404).body(new ResponseMessageWrapper("Could not find user with "));
        }
    }

    @PostMapping(value = "/register/patient")
    public ResponseEntity registerPatient(@Valid @RequestBody AppUserRegisterDTO user) {
        AppUser newAppUser = modelMapper.map(user, AppUser.class);
        try {
            userService.registerPatient(newAppUser);
        } catch (PeselExists | EmailExists | UserExists error) {
            return ResponseEntity.status(400).body(new ResponseMessageWrapper(error.getMessage()));
        }
        return ResponseEntity.ok(new ResponseMessageWrapper("Patient account successfully created"));
    }

    @PostMapping(value = "/register/doctor")
    public ResponseEntity registerDoctor(@Valid @RequestBody AppUserRegisterDTO user) {
        AppUser newAppUser = modelMapper.map(user, AppUser.class);
        try {
            userService.registerDoctor(newAppUser);
        } catch (PeselExists | EmailExists | UserExists error) {
            return ResponseEntity.status(400).body(new ResponseMessageWrapper(error.getMessage()));
        }
        return ResponseEntity.ok(new ResponseMessageWrapper("Doctor account successfully created"));
    }
}
