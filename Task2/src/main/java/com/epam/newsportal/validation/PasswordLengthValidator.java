package com.epam.newsportal.validation;

import com.epam.newsportal.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Transactional
public class PasswordLengthValidator implements Validator {

    @Autowired
    private Rejecter rejecter;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = ((User) o);
        int min = 6;
        int max = 40;
        if (user.getPassword().length() < min || user.getPassword().length() > max) {
            rejecter.reject(errors, "password", "", "validation.password.length", new Object[]{min,max});
        }
        if (user.getPasswordRepeat().length() < min || user.getPassword().length() > max) {
            rejecter.reject(errors, "passwordRepeat", "", "validation.password.length", new Object[]{min,max});

        }
    }
}
