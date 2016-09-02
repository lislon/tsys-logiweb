/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.filter;

import com.tsystems.javaschool.logiweb.api.helper.ServicesFacade;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Igor Avdeev on 9/2/16.
 */
public class EntityManagerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServicesFacade servicesFacade = new ServicesFacade();
        request.setAttribute("servicesFacade", servicesFacade);
        try {
            chain.doFilter(request, response);
        } finally {
            servicesFacade.closeEm();
        }
    }

    @Override
    public void destroy() {

    }
}
