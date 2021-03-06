package com.epam.newsportal.validation;

import com.epam.newsportal.model.entity.User;
import com.epam.newsportal.util.Rejecter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Transactional
public class PasswordRepeatValidator implements Validator {

    private static final String MESSAGE = "validation.password.repeat";
    private static final String PASSWORD_REPEAT = "passwordRepeat";

    @Autowired
    private Rejecter rejecter;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User userTransfer = ((User) o);
        if (!userTransfer.getPassword().equals(userTransfer.getPasswordRepeat())) {
            rejecter.reject(errors, PASSWORD_REPEAT, "", MESSAGE, new Object[0]);
        }
    }
}
