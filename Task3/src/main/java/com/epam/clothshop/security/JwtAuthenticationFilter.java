package com.epam.clothshop.security;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtGenerator jwtGenerator;

    private final static String API_PATH = "/api";
    private final static String DENY_PATH = "/api/denied";
    private final static String REGISTRATION_PATH = "/api/user";
    private final static String LOGIN_PATH = "/api/login";
    private final static String LOGOUT_PATH = "/api/logout";
    private final static String POST = "POST";

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (
                !request.getRequestURI().startsWith(request.getContextPath().concat(API_PATH))
                || request.getRequestURI().equals(request.getContextPath().concat(DENY_PATH))
                || (request.getRequestURI().equals(request.getContextPath().concat(DENY_PATH)) && request.getMethod().equals(POST))
        ) {
            filterChain.doFilter(request, response);
        } else {
            if (request.getRequestURI().equals(request.getContextPath().concat(LOGIN_PATH)) && request.getMethod().equals(POST)) {
                login(request, response, filterChain);
                return;
            }
            if (request.getRequestURI().equals(request.getContextPath().concat(LOGOUT_PATH)) && request.getMethod().equals(POST)) {
                logout(request, response, filterChain);
                return;
            }
            validate(request, response, filterChain);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Optional<Cookie> optional = jwtGenerator.extractCookie(request);
        if (optional.isPresent()) {
            deny(HttpServletResponse.SC_FORBIDDEN, request, response, filterChain);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Optional<Cookie> optional = jwtGenerator.extractCookie(request);
        if (!optional.isPresent()) {
            deny(HttpServletResponse.SC_FORBIDDEN, request, response, filterChain);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void validate(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Optional<Cookie> optional = jwtGenerator.extractCookie(request);
        if (!optional.isPresent()) {
            deny(HttpServletResponse.SC_FORBIDDEN, request, response, filterChain);
        } else {
            SecurityModel securityModel = null;
            try {
                securityModel = jwtGenerator.parseCookie(optional.get());
            } catch (JwtException e) {
                e.printStackTrace();
                deny(HttpServletResponse.SC_FORBIDDEN, request, response, filterChain);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    securityModel.getId(), null, securityModel.getAuthorities()
            ));
            response.addCookie(jwtGenerator.generateCookie(securityModel));
            filterChain.doFilter(request, response);
        }
    }

    private void deny(int code, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.getRequestDispatcher(DENY_PATH).forward(request, response);
    }
}
