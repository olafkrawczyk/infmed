package com.gubkra.infmed.infmedRest.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */

@Entity
@Data
public class Patient {
    @Id
    UUID uuid;

    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String surname;
    @Column(unique = true, nullable = false)
    String pesel;
    @Column(nullable = false)
    LocalDate birthDate;

    @Column(nullable = false)
    String phoneNumber;

    @Column(unique = true, nullable = false)
    String emailAddress;

    @Column(unique = true, nullable = false)
    String username;
    String password;

    @OneToOne()
    Address address;
}
