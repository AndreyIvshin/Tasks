package com.epam.clothshop.security;

import java.util.Arrays;
import java.util.List;

import static com.epam.clothshop.security.Authority.READ;
import static com.epam.clothshop.security.Authority.WRITE;

public enum Role {
    USER(new Authority[]{READ}),
    ADMIN(new Authority[]{READ, WRITE});

    private Authority[] authorities;

    Role(Authority[] authorities) {
        this.authorities = authorities;
    }

    public List<Authority> getAuthorities() {
        return Arrays.asList(authorities);
    }
}
