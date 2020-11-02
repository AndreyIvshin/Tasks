package com.epam.newsportal.controller;

import com.epam.newsportal.model.entity.User;
import com.epam.newsportal.repository.DatabaseFiller;
import com.epam.newsportal.service.UserService;
import com.epam.newsportal.validation.PasswordLengthValidator;
import com.epam.newsportal.validation.PasswordRepeatValidator;
import com.epam.newsportal.validation.UsernameUniqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Transactional
@ControllerAdvice
public class UserController {

    @Autowired
    private DatabaseFiller databaseFiller;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordRepeatValidator passwordRepeatValidator;
    @Autowired
    private PasswordLengthValidator passwordLengthValidator;
    @Autowired
    private UsernameUniqueValidator usernameUniqueValidator;

    @GetMapping("/")
    public String index() {
        databaseFiller.init();
        return "index";
    }

    @GetMapping("/sign/in")
    public String signIn() {
        return "signIn";
    }

    @PostMapping("/sign/in/process")
    public String signInProcess() {
        return "redirect:/";
    }

    @GetMapping("/sign/up")
    public String signUp(@RequestParam Boolean error, Model model) {
        model.addAttribute("user", new User());
        return "signUp";
    }

    @PostMapping("/sign/up/process")
    public String signUpProcess(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
        passwordRepeatValidator.validate(user, bindingResult);
        passwordLengthValidator.validate(user, bindingResult);
        usernameUniqueValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "signUp";
        } else {
            userService.create(user);
            return "redirect:/";
        }
    }

    @GetMapping("/denied")
    public String denied() {
        return "denied";
    }

//    @ExceptionHandler(Throwable.class)
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
