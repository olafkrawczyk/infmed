package com.gubkra.infmed.infmedRest.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Olaf on 2018-03-18.
 */

@Data
@Entity
public class Privilege {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    Long id;

    String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
