package com.epam.clothshop.api;

import com.epam.clothshop.security.JwtGenerator;
import com.epam.clothshop.security.SecurityModel;
import com.epam.clothshop.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
@ControllerAdvice
public class SecurityController {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtGenerator jwtGenerator;


    @PostMapping("api/login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        UserDetails userDetails = securityService.loadUserByUsername(username);
        if (userDetails != null) {
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                response.addCookie(jwtGenerator.generateCookie((SecurityModel) userDetails));
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("api/logout")
    public ResponseEntity logout(HttpServletResponse response) {
        response.addCookie(jwtGenerator.generateInvalidationCookie());
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @RequestMapping("api/denied")
    public ResponseEntity accessDenied() {
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
    }
}
