package com.gubkra.infmed.infmedRest.domain.dto;

import com.gubkra.infmed.infmedRest.utils.validators.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Olaf on 2018-03-26.
 */
@PasswordMatches
@Data
public class AppUserRegisterDTO extends AppUserDTO {
    @NotNull
    private String password;
    @NotNull
    private String matchingPassword;
}
