package com.epam.clothshop.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
public class SecurityController {

    @GetMapping
    public ResponseEntity defaultPath(HttpServletResponse response) {
        return ResponseEntity.status(response.getStatus()).build();
    }

    @PostMapping("api/security/login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        return ResponseEntity.status(response.getStatus()).build();
    }

    @PostMapping("api/security/logout")
    public ResponseEntity logout(HttpServletResponse response) {
        return ResponseEntity.status(response.getStatus()).build();
    }
}
