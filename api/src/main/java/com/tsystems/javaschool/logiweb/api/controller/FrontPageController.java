package com.tsystems.javaschool.logiweb.api.controller;

import java.util.Collection;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.tsystems.javaschool.logiweb.entities.status.UserRole;
//import com.tsystems.javaschool.logiweb.model.DriverUserModel;

@Controller
public class FrontPageController {

    @RequestMapping("/")
    public String commonFrontPage() {
//        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
//                .getContext().getAuthentication().getAuthorities();
//
//        GrantedAuthority managerRole = new SimpleGrantedAuthority(
//                UserRole.ROLE_MANAGER.name());
//        GrantedAuthority driverRole = new SimpleGrantedAuthority(
//                UserRole.ROLE_DRIVER.name());
//
//        if (authorities.contains(managerRole)) {
//            return "forward:/manager";
//        } else if (authorities.contains(driverRole)) {
//            DriverUserModel logedInDriver = (DriverUserModel) SecurityContextHolder.getContext()
//                    .getAuthentication().getPrincipal();
//            int driverId = logedInDriver.getDriverLogiwebId();
//            return "forward:/driver/" + driverId;
//        }
//
//        throw new AccessDeniedException("User role is unknown.");
        return "forward:/orders";
    }
}