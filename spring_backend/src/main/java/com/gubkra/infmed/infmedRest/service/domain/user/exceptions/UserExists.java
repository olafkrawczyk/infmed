package com.gubkra.infmed.infmedRest.service.domain.user.exceptions;

public class UserExists extends Exception {
    public UserExists(String message) {
        super(message);
    }
}
