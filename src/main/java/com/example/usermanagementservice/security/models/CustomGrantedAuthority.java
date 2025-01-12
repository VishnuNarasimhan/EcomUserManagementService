package com.example.usermanagementservice.security.models;

import com.example.usermanagementservice.models.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {
    // Role <==> GrantedAuthority
    private String authority;

    private CustomGrantedAuthority() {}

    public CustomGrantedAuthority(Role role) {
        this.authority = role.getRoleName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
