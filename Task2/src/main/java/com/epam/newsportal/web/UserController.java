package com.epam.newsportal.web;

import com.epam.newsportal.security.Secured;
import com.epam.newsportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.epam.newsportal.persistence.enumeration.Role.*;

@Controller
@Transactional
@Secured(GUEST)
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;
    @Autowired
    private NewsController newsController;
    @Autowired
    private SecurityController securityController;

    @GetMapping("/user/sign/up")
    public ModelAndView signUp() {
        return new ModelAndView("signUp");
    }

    @GetMapping("/user/sign/in")
    public ModelAndView signIn() {
        return new ModelAndView("signIn");
    }

    @PostMapping("/user/sign/up")
    public ModelAndView signUp(@RequestParam String username, @RequestParam String password, @RequestParam String passwordRepeat) {
        if (userService.signUp(username, password, passwordRepeat)) {
            return newsController.index();
        } else {
            return securityController.accessDenied();
        }
    }

    @PostMapping("/user/sign/in")
    public ModelAndView signIn(@RequestParam String username, @RequestParam String password) {
        if (userService.signIn(username, password)) {
            return newsController.index();
        } else {
            return securityController.accessDenied();
        }
    }

    @Secured({USER, ADMIN})
    @GetMapping("/user/sign/out")
    public ModelAndView signOut() {
        userService.logout();
        return newsController.index();
    }
}
