package com.epam.newsportal.validation;

import com.epam.newsportal.mapper.UserMapper;

import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordRepeatValidator implements ConstraintValidator<PasswordRepeat, UserMapper.UserReg> {

    @Override
    public void initialize(PasswordRepeat constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserMapper.UserReg userReg, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(1);
        return userReg.getPassword().equals(userReg.getPasswordRepeat());
    }
}
