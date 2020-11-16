package com.epam.clothshop.api;

import com.epam.clothshop.mapper.UserMapper;
import com.epam.clothshop.security.JwtGenerator;
import com.epam.clothshop.security.SecurityModel;
import com.epam.clothshop.security.SecurityService;
import com.epam.clothshop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Transactional
@RestController
@RequestMapping
@ControllerAdvice
@Api(tags = "security")
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
    @Operation(summary = "Login")
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
    @Operation(summary = "Logout")
    public ResponseEntity logout(HttpServletResponse response) {
        response.addCookie(jwtGenerator.generateInvalidationCookie());
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @RequestMapping("api/denied")
    @Operation(summary = "Access denied")
    public ResponseEntity accessDenied() {
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
    }
}
