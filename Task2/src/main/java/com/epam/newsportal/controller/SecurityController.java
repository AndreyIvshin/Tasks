package com.epam.newsportal.controller;

import com.epam.newsportal.model.entity.User;
import com.epam.newsportal.repository.DatabaseFiller;
import com.epam.newsportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@Transactional
@ControllerAdvice
public class SecurityController {

    @Autowired
    private DatabaseFiller databaseFiller;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        databaseFiller.init();
        return "/index";
    }

    @GetMapping("/sign/in")
    public String signIn() {
        return "signIn";
    }

    @GetMapping("/sign/up")
    public String signUp() {
        return "signUp";
    }

    @PostMapping("/sign/in/process")
    public String signInProcess() {
        return "redirect:/";
    }

    @PostMapping("/sign/up/process")
    public String signUpProcess(@ModelAttribute User user) {
        userService.create(user);
        return "redirect:/";
    }

    @GetMapping("/denied")
    public String denied() {
        return "denied";
    }

    @ExceptionHandler(Throwable.class)
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
