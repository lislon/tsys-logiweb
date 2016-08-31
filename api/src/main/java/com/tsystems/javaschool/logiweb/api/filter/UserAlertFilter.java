/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.filter;

import com.tsystems.javaschool.logiweb.api.helper.UserAlert;
import org.springframework.http.server.ServerHttpRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Remove alert for user once shown after response.
 */
public class UserAlertFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            session.removeAttribute(UserAlert.ATTR_NAME);
        }
    }

    @Override
    public void destroy() {

    }
}
