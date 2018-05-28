package com.gubkra.infmed.infmedRest.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */

@Entity
@Data
public class AppUser {
    @Id
    private UUID uuid;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(unique = true, nullable = false)
    private String pesel;
    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String emailAddress;

    @Column(unique = true, nullable = false)
    private String username;
    private String password;

    @OneToOne()
    private Address address;

    
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

    @ManyToMany
    @JoinTable(name = "doctor_patient",
    joinColumns = @JoinColumn(name = "doctor_uuid"),
    inverseJoinColumns = @JoinColumn(name = "patient_uuid"))
    private Collection<AppUser> patients;

    @ManyToMany(mappedBy = "patients")
    private Collection<AppUser> doctors;

    @OneToMany(mappedBy = "patient")
    private Collection<MedicalExamination> examinations;

    @Override
    public String toString(){
        return "[User]"+this.uuid + " : " + this.getUsername();
    }

    public ArrayList<AppUser> getDoctors() {
        return this.doctors != null ? new ArrayList<>(this.doctors) : new ArrayList<>();
    }

    public Collection<AppUser> getPatients() {
        return this.patients;
    }

    public String getUsername() {
        return this.username;
    }
}
