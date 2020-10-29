package com.epam.newsportal.web;

import com.epam.newsportal.persistence.enumeration.Role;
import com.epam.newsportal.security.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
@Secured({Role.GUEST, Role.USER, Role.ADMIN})
public class SecurityController extends AbstractController {

    @RequestMapping("/access/denied")
    public ModelAndView accessDenied() {
        return new ModelAndView("accessDenied");
    }

    @RequestMapping("/error")
    public ModelAndView error() {
        return new ModelAndView("error");
    }

    @RequestMapping("/not/found")
    public ModelAndView notFound() {
        return new ModelAndView("notFound");
    }
}
