package com.epam.newsportal.mapper;

import com.epam.newsportal.enumeration.Role;
import com.epam.newsportal.model.User;
import com.epam.newsportal.validation.PasswordRepeat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;

public class UserMapper {

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserAuth {
        @Size(min = 4, max = 40)
        String username;
        @Size(min = 4, max = 40)
        String password;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @PasswordRepeat
    public static class UserReg {
        @Size(min = 4, max = 40)
        String username;
        @Size(min = 4, max = 40)
        String password;
        @Size(min = 4, max = 40)
        String passwordRepeat;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserRole {
        Role role;
    }

    public User mapReg(UserReg userReg) {
        User user = new User();
        user.setUsername(userReg.getUsername());
        user.setPassword(userReg.getPassword());
        user.setPasswordRepeat(userReg.getPasswordRepeat());
        return user;
    }

}
