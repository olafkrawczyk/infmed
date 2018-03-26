package com.gubkra.infmed.infmedRest.utils.validators;

import com.gubkra.infmed.infmedRest.domain.dto.UserRegisterDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserRegisterDTO user = (UserRegisterDTO) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}