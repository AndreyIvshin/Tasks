package com.epam.newsportal.security;

import com.epam.newsportal.enumeration.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JWTGenerator {

    private String secret = "12345678123456781234567812345678123456781234567812345678123456781234567812345678";
    private String ROLE = "role";

    public String generate(Role role) {
        Instant now = Instant.now();
        return "Bearer " + Jwts.builder()
                .claim(ROLE, role.toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Role parse(String token) {
        return Role.valueOf(Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token.split(" ")[1])
                .getBody()
                .get(ROLE, String.class));
    }
}
