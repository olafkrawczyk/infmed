package com.gubkra.infmed.infmedRest.domain;

import lombok.Data;
import javax.persistence.*;

/**
 * Created by Olaf on 2018-03-11.
 */

@Entity
@Data
public class Address {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    Long id;

    @Column(nullable = false)
    String city;
    @Column(nullable = false)
    String postalCode;
    @Column(nullable = false)
    String street;
    @Column(nullable = false)
    String houseNumber;
    String apartmentNumber;
}
