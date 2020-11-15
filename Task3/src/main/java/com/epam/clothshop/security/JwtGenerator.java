package com.epam.clothshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtGenerator {

    @Value("${jwt.secret}")
    private String secret;

    private final static String JWT = "jwt";
    private final static String COOKIE_PATH = "/";
    private final static int COOKIE_EXPIRATION_SECONDS = 300;
    private final static int TOKEN_EXPIRATION_MINUTES = 5;
    private final static String SECURITY_MODEL_ID = "id";
    private final static String SECURITY_MODEL_AUTHORITIES = "authorities";
    private final static String AUTHORITY_KEY = "authority";

    public Cookie generateCookie(SecurityModel securityModel) {
        securityModel.setPassword("");
        Cookie cookie = new Cookie(JWT, generateToken(securityModel));
        cookie.setMaxAge(COOKIE_EXPIRATION_SECONDS);
        cookie.setHttpOnly(true);
        cookie.setPath(COOKIE_PATH);
        return cookie;
    }

    public Cookie generateInvalidationCookie() {
        Cookie cookie = new Cookie(JWT, "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath(COOKIE_PATH);
        return cookie;
    }

    public SecurityModel parseCookie(Cookie cookie) {
        String token = cookie.getValue();
        return parseToken(token);
    }

    public String generateToken(SecurityModel securityModel) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claim(SECURITY_MODEL_ID, securityModel.getId())
                .claim(SECURITY_MODEL_AUTHORITIES, securityModel.getAuthorities())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(TOKEN_EXPIRATION_MINUTES, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public SecurityModel parseToken(String token) {
        Jws<Claims>
                jws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token);
        SecurityModel securityModel = new SecurityModel();
        securityModel.setId(jws.getBody().get(SECURITY_MODEL_ID, Long.class));
        List list = jws.getBody().get(SECURITY_MODEL_AUTHORITIES, ArrayList.class);
        List<String> strings = (List<String>) list.stream().map(m -> ((LinkedHashMap) m).get(AUTHORITY_KEY)).collect(Collectors.toList());
        List<? extends GrantedAuthority> authorities = strings.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        securityModel.setAuthorities(authorities);
        return securityModel;
    }

    public Optional<Cookie> extractCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(JWT)).findAny();
        } else {
            return Optional.<Cookie>empty();
        }
    }
}
