package com.epam.newsportal.service;

import com.epam.newsportal.model.User;
import com.epam.newsportal.repository.UserRepository;

import java.util.Optional;

public class UserService extends AbstractService<User, Long, UserRepository> {

    public Optional<User> findUserByUsername(String username) {
        return repository.findUserByUsername(username);
    }
}
