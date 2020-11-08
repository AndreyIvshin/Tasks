package com.epam.newsportal.validation;

import com.epam.newsportal.model.entity.User;
import com.epam.newsportal.model.transfer.UserTransfer;
import com.epam.newsportal.util.Rejecter;
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
        return UserTransfer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserTransfer userTransfer = ((UserTransfer) o);
        int min = 4;
        int max = 40;
        if (userTransfer.getPassword().length() < min || userTransfer.getPassword().length() > max) {
            rejecter.reject(errors, "password", "", "validation.password.length", new Object[]{min,max});
        }
        if (userTransfer.getPasswordRepeat().length() < min || userTransfer.getPassword().length() > max) {
            rejecter.reject(errors, "passwordRepeat", "", "validation.password.length", new Object[]{min,max});

        }
    }
}
