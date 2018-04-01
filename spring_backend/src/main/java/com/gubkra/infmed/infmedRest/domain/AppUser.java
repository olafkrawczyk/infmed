package com.gubkra.infmed.infmedRest.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */

@Entity
@Data
public class AppUser {
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

    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            ))
    private Collection<Role> roles;

    @Override
    public String toString(){
        return "[User]"+this.uuid + " : " + this.getUsername();
    }
}
