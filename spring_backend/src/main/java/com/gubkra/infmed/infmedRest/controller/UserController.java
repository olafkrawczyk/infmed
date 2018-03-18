package com.gubkra.infmed.infmedRest.controller;

import com.google.common.collect.Streams;
import com.gubkra.infmed.infmedRest.domain.dto.UserDTO;
import com.gubkra.infmed.infmedRest.service.domain.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olaf on 2018-03-11.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping(value = "")
    public List<UserDTO> getAll() {
        return Streams.stream(userService.findAll()).map(x -> modelMapper.map(x, UserDTO.class)).collect(Collectors.toList());
    }
}
