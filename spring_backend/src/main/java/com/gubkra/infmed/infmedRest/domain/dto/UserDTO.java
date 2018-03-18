package com.gubkra.infmed.infmedRest.domain.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by Olaf on 2018-03-18.
 */

@Data
public class UserDTO {
    UUID uuid;
    String name;
    String surname;
    String pesel;
    LocalDate birthDate;
    String phoneNumber;
    String emailAddress;
    String username;
}
