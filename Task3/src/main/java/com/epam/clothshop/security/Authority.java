package com.epam.clothshop.security;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    READ,
    WRITE;

    @Override
    public String getAuthority() {
        return toString();
    }
}
