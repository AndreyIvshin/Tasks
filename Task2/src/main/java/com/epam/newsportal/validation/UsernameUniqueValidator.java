package com.epam.newsportal.validation;

import com.epam.newsportal.model.entity.User;
import com.epam.newsportal.model.transfer.UserTransfer;
import com.epam.newsportal.service.UserService;
import com.epam.newsportal.util.Rejecter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Transactional
public class UsernameUniqueValidator implements Validator {

    @Autowired
    private Rejecter rejecter;
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserTransfer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserTransfer newUser = ((UserTransfer) o);
        UserTransfer oldUser = (UserTransfer) userService.loadUserByUsername(newUser.getUsername());
        if (oldUser != null) {
            rejecter.reject(errors, "username", "", "validation.user.exists", new Object[0]);
        }
    }
}
