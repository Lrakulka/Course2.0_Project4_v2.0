package com.epam.model.dto.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by fg on 7/27/2016.
 *
 * Realization of validator for the email.
 */

class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    @Autowired
    private Environment environment;

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN = "email.pattern";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        return (validateEmail(email));
    }

    private boolean validateEmail(String email) {
        pattern = Pattern.compile(environment.getRequiredProperty(EMAIL_PATTERN));
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
