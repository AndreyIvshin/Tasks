package com.epam.newsportal.model.enumeration;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

import static com.epam.newsportal.model.enumeration.Authority.READ_NEWS;
import static com.epam.newsportal.model.enumeration.Authority.WRITE_NEWS;

public enum Role {
    USER(new Authority[]{READ_NEWS}),
    ADMIN(new Authority[]{READ_NEWS, WRITE_NEWS});

    private Authority[] authorities;

    Role(Authority[] authorities) {
        this.authorities = authorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(authorities);
    }
}
