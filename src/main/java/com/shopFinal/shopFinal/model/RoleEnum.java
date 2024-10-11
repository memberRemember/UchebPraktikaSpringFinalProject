package com.shopFinal.shopFinal.model;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    USER, ADMIN, EMPLOYEE, MANAGER;

    @Override
    public String getAuthority(){
        return name();
    }
}
