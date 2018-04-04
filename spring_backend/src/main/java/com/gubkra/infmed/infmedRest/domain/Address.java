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
    private Long id;

    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String houseNumber;
    private String apartmentNumber;
}
