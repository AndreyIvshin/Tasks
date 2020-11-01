package com.epam.newsportal.model.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    READ_NEWS("READ_NEWS"),
    WRITE_NEWS("WRITE_NEWS");

    private String authority;

    Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
