package com.gubkra.infmed.infmedRest.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public abstract class MedicalExamination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "patient_uudid", nullable = false)
    private AppUser patient;
}
