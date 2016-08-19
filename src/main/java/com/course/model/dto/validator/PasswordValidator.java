package com.course.model.dto.validator;

import com.course.model.dto.UserRegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by fg on 7/27/2016.
 *
 * Realization of validator for the registration password.
 */

class PasswordValidator implements ConstraintValidator<ValidPassword, UserRegistrationDTO> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {}

    @Override
    public boolean isValid(UserRegistrationDTO value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getMatchingPassword());
    }
}
