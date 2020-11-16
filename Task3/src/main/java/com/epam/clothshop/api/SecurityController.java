package com.epam.clothshop.api;

import com.epam.clothshop.mapper.UserMapper;
import com.epam.clothshop.security.JwtGenerator;
import com.epam.clothshop.security.SecurityModel;
import com.epam.clothshop.security.SecurityService;
import com.epam.clothshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtGenerator jwtGenerator;


    @PostMapping("api/login")
    public ResponseEntity<UserMapper.UserFull> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        SecurityModel securityModel = (SecurityModel) securityService.loadUserByUsername(username);
        if (securityModel != null) {
            if (passwordEncoder.matches(password, securityModel.getPassword())) {
                response.addCookie(jwtGenerator.generateCookie(securityModel));
                return userService.findById(securityModel.getId()).map(user -> ResponseEntity.ok(userMapper.mapFull(user)))
                        .orElseGet(() -> ResponseEntity.notFound().build());
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
