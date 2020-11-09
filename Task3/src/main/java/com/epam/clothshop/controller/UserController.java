package com.epam.clothshop.controller;

import com.epam.clothshop.model.User;
import com.epam.clothshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@Transactional
public class UserController {

    @Autowired private UserRepository userRepository;

    @GetMapping
    public List<User> get() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        userRepository.save(admin);
        userRepository.save(user);
        return (List<User>) userRepository.findAll();
    }
}
