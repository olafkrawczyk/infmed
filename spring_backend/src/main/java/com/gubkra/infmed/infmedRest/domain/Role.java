package com.gubkra.infmed.infmedRest.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Olaf on 2018-03-18.
 */

@Data
@Entity
public class Role {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    Long id;

    String name;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"
            ))
    private Collection<Privilege> privileges;

}
