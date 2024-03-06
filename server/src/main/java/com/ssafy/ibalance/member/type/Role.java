package com.ssafy.ibalance.member.type;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER("ROLE_USER", "유저권한");

    private String authority;
    private String description;

    private Role(String authority, String description) {
        this.authority = authority;
        this.description = description;
    }
    @Override
    public String getAuthority() {
        return this.authority;
    }
}
