package com.tsystems.javaschool.logiweb.api.controller;

import com.tsystems.javaschool.logiweb.dao.entities.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//import com.tsystems.javaschool.logiweb.entities.status.UserRole;
//import com.tsystems.javaschool.logiweb.model.DriverUserModel;

@Controller
public class FrontPageController {


    @RequestMapping("/")
    public void commonFrontPage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request.isUserInRole(User.UserRole.ROLE_MANAGER.toString())) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/orders"));
        } else if (request.isUserInRole(User.UserRole.ROLE_DRIVER.toString())) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myorder"));
        }

        throw new AccessDeniedException("User role is unknown.");
    }
}