package com.epam.clothshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final static String JWT = "jwt";
    private final static String LOGIN_PATH = "/api/security/login";
    private final static String LOGOUT_PATH = "/api/security/logout";
    private final static String POST = "POST";
    private final static String API_PATH = "/api";
    private final static String USERNAME_PARAMETER = "username";
    private final static String PASSWORD_PARAMETER = "password";
    private final static String PRINCIPAL = "principal";
    private final static String COOKIE_PATH = "/";
    private final static int COOKIE_EXPIRATION_SECONDS = 300;
    private final static int TOKEN_EXPIRATION_MINUTES = 5;
    private final static String SECURITY_MODEL_ID = "id";
    private final static String SECURITY_MODEL_USERNAME = "username";
    private final static String SECURITY_MODEL_PASSWORD = "password";
    private final static String SECURITY_MODEL_AUTHORITIES = "authorities";

    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (request.getRequestURI().equals(request.getContextPath().concat(LOGIN_PATH)) && request.getMethod().equals(POST)) {
            login(request, response, filterChain);
            return;
        }
        if (request.getRequestURI().equals(request.getContextPath().concat(LOGOUT_PATH)) && request.getMethod().equals(POST)) {
            logout(request, response, filterChain);
            return;
        }
        if (request.getRequestURI().startsWith(request.getContextPath().concat(API_PATH))) {
            validate(request, response, filterChain);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Optional<Cookie> optional = extractCookie(request);
        if (optional.isPresent()) {
            setStatus(HttpServletResponse.SC_FORBIDDEN, request, response, filterChain);
        } else {
            String username = request.getParameter(USERNAME_PARAMETER);
            String password = request.getParameter(PASSWORD_PARAMETER);
            UserDetails userDetails = securityService.loadUserByUsername(username);
            if (userDetails != null) {
                if (passwordEncoder.matches(password, userDetails.getPassword())) {
                    response.addCookie(generateCookie((SecurityModel) userDetails));
                    filterChain.doFilter(request, response);
                } else {
                    setStatus(HttpServletResponse.SC_FORBIDDEN, request, response, filterChain);
                }
            } else {
                setStatus(HttpServletResponse.SC_NOT_FOUND, request, response, filterChain);
            }
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Optional<Cookie> optional = extractCookie(request);
        if (!optional.isPresent()) {
            setStatus(HttpServletResponse.SC_FORBIDDEN, request, response, filterChain);
        } else {
            response.addCookie(generateInvalidationCookie());
            filterChain.doFilter(request, response);
        }
    }

    private void validate(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Optional<Cookie> optional = extractCookie(request);
        if (!optional.isPresent()) {
            setStatus(HttpServletResponse.SC_FORBIDDEN, request, response, filterChain);
        } else {
            SecurityModel securityModel = null;
            try {
                securityModel = parseCookie(optional.get());
            } catch (JwtException e) {
                e.printStackTrace();
                setStatus(HttpServletResponse.SC_FORBIDDEN, request, response, filterChain);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    securityModel, null, securityModel.getAuthorities()
            ));
            response.addCookie(generateCookie(securityModel));
            filterChain.doFilter(request, response);
        }
    }

    private Optional<Cookie> extractCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(JWT)).findAny();
        } else {
            return Optional.<Cookie>empty();
        }
    }

    private Cookie generateCookie(SecurityModel securityModel) {
        securityModel.setPassword("null");
        Cookie cookie = new Cookie(JWT, generateToken(securityModel));
        cookie.setMaxAge(COOKIE_EXPIRATION_SECONDS);
        cookie.setHttpOnly(true);
        cookie.setPath(COOKIE_PATH);
        return cookie;
    }

    private Cookie generateInvalidationCookie() {
        Cookie cookie = new Cookie(JWT, "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath(COOKIE_PATH);
        return cookie;
    }

    private SecurityModel parseCookie(Cookie cookie) {
        String token = cookie.getValue();
        return parseToken(token);
    }

    private String generateToken(SecurityModel securityModel) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claim(
                        PRINCIPAL, securityModel)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(TOKEN_EXPIRATION_MINUTES, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    private SecurityModel parseToken(String token) {
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token);
        LinkedHashMap linkedHashMap = jws.getBody().get(PRINCIPAL, LinkedHashMap.class);
        SecurityModel securityModel = new SecurityModel();
        securityModel.setId(Long.valueOf(String.valueOf(linkedHashMap.get(SECURITY_MODEL_ID))));
        securityModel.setUsername(String.valueOf(linkedHashMap.get(SECURITY_MODEL_USERNAME)));
        List authorities = (List) linkedHashMap.get(SECURITY_MODEL_AUTHORITIES);
        securityModel.setAuthorities((List<? extends Authority>) authorities.stream().map(a -> Authority.valueOf(String.valueOf(a))).collect(Collectors.toList()));
        return securityModel;
    }

    private void setStatus(int code, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        response.setStatus(code);
        filterChain.doFilter(request, response);
    }
}
