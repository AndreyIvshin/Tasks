package com.epam.newsportal.model.enumeration;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

public enum Role {
    USER(new Authority[]{Authority.READ_NEWS}),
    ADMIN(new Authority[]{Authority.READ_NEWS, Authority.WRITE_NEWS});

    private Authority[] authorities;

    Role(Authority[] authorities) {
        this.authorities = authorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(authorities);
    }
}
