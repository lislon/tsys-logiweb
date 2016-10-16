package com.tsystems.javaschool.logiweb.service.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class DriverUserModel extends UserModel {

    private int driverId;

    public DriverUserModel(String username, String password,
            Collection<? extends GrantedAuthority> authorities,
            int driverId) {
        super(username, password, authorities);
        this.driverId = driverId;
    }

    public int getDriverId() {
        return driverId;
    }
}
