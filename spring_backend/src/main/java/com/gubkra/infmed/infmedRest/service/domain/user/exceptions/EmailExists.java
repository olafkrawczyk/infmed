package com.gubkra.infmed.infmedRest.service.domain.user.exceptions;

public class EmailExists extends Exception {
    public EmailExists(String message) {
        super(message);
    }
}
