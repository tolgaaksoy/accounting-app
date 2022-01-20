package com.tolgaaksoy.accountingapp.model.entity.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_ADMIN,
    ROLE_ACCOUNTANT;

    public String getAuthority() {
        return name();
    }
}
