package com.gubkra.infmed.infmedRest.utils.validators;

import com.gubkra.infmed.infmedRest.domain.dto.AppUserRegisterDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        AppUserRegisterDTO user = (AppUserRegisterDTO) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}