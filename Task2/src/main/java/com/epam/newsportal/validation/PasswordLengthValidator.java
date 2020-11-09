package com.epam.newsportal.validation;

import com.epam.newsportal.model.entity.User;
import com.epam.newsportal.util.Rejecter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Transactional
public class PasswordLengthValidator implements Validator {

    private static final String MESSAGE = "validation.password.repeat";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_REPEAT = "passwordRepeat";

    @Value("${password.min}") private String minProperty;
    @Value("${password.max}") private String maxProperty;

    @Autowired private Rejecter rejecter;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User userTransfer = ((User) o);
        int min = Integer.parseInt(minProperty);
        int max = Integer.parseInt(maxProperty);
        if (userTransfer.getPassword().length() < min || userTransfer.getPassword().length() > max) {
            rejecter.reject(errors, PASSWORD, "", MESSAGE, new Object[]{min,max});
        }
        if (userTransfer.getPasswordRepeat().length() < min || userTransfer.getPassword().length() > max) {
            rejecter.reject(errors, PASSWORD_REPEAT, "", MESSAGE, new Object[]{min,max});

        }
    }
}
