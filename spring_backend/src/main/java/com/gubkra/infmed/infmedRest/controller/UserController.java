package com.gubkra.infmed.infmedRest.controller;

import com.gubkra.infmed.infmedRest.domain.User;
import com.gubkra.infmed.infmedRest.service.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Olaf on 2018-03-11.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "")
    public Iterable<User> getAll() {
        return userService.findAll();
    }
}
