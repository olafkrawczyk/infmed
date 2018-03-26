package com.gubkra.infmed.infmedRest.controller;

import com.google.common.collect.Streams;
import com.gubkra.infmed.infmedRest.domain.User;
import com.gubkra.infmed.infmedRest.domain.dto.UserDTO;
import com.gubkra.infmed.infmedRest.domain.dto.UserRegisterDTO;
import com.gubkra.infmed.infmedRest.service.domain.user.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olaf on 2018-03-11.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping(value = "")
    public List<UserDTO> getAll() {
        return Streams.stream(userService.findAll()).map(x -> modelMapper.map(x, UserRegisterDTO.class)).collect(Collectors.toList());
    }

    @PostMapping(value = "/register")
    public ResponseEntity registerUser(@Valid @RequestBody UserRegisterDTO user) {
        User newUser = modelMapper.map(user, User.class);
        userService.addItem(newUser);
        return ResponseEntity.ok("Saved");
    }

}
