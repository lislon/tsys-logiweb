package com.tsystems.javaschool.logiweb.api.helper;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.model.DriverUserModel;
import com.tsystems.javaschool.logiweb.service.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Saves the user details in session after user has logged in.
 *
 */
@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationHandler.class);

    @Autowired
    private DriverManager driverManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String userTitle = "Logiweb manager";
        String userStatus = "online";
        String personalCode = "";

        try {

            UserModel principal = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof DriverUserModel) {
                Driver driver = driverManager.findOneOrFail(((DriverUserModel) principal).getDriverId());
                userTitle = driver.getFirstName() + " " + driver.getLastName();
                userStatus = driver.getStatus().toString();
                personalCode = driver.getPersonalCode();
            }
        } catch (EntityNotFoundException e) {
            userTitle = "Unknown";
            logger.error("Error while retrieving driver entity from user credentials", e);
        }

        HttpSession session = request.getSession();
        session.setAttribute("userTitle", userTitle);
        session.setAttribute("userStatus", userStatus);
        session.setAttribute("userPersonalCode", personalCode);

        response.sendRedirect(request.getContextPath() + "/");
    }
}
