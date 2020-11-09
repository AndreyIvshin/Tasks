package com.epam.newsportal.validation;

import com.epam.newsportal.service.UserService;
import com.epam.newsportal.model.entity.User;
import com.epam.newsportal.util.Rejecter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Transactional
public class UsernameUniqueValidator implements Validator {

    private static final String MESSAGE = "validation.user.exists";
    private static final String USERNAME = "username";

    @Autowired
    private Rejecter rejecter;
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User newUser = ((User) o);
        User oldUser = (User) userService.loadUserByUsername(newUser.getUsername());
        if (oldUser != null) {
            rejecter.reject(errors, USERNAME, "", MESSAGE, new Object[0]);
        }
    }
}
