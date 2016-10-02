package com.tsystems.javaschool.logiweb.service.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Custom user model for Spring Security.
 */
public class UserModel extends User {

    public UserModel(String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
