package com.gubkra.infmed.infmedRest.domain.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by Olaf on 2018-03-18.
 */

@Data
public class AppUserDTO {
   private UUID uuid;

   @NotNull
   @Size(min = 2)
   private String name;
   @NotNull
   @Size(min = 2)
   private String surname;
   @NotNull
   @Size(min = 11, max = 11)
   private String pesel;
   @NotNull
   private LocalDate birthDate;
   @NotNull
   private String phoneNumber;
   @NotNull
   private String emailAddress;
   @NotNull
   @Size(min = 6, message = "username must be at least 6 characters long")
   private String username;
}
