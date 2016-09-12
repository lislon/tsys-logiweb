/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.filter;

import com.tsystems.javaschool.logiweb.dao.helper.LocalEntityManagerFactory;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;

import javax.persistence.EntityManager;
import javax.servlet.*;
import java.io.IOException;

/**
 * Creation and destruction of EntityManager per each request.
 */
public class EntityManagerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        EntityManager em = LocalEntityManagerFactory.createEntityManager();
        ServiceContainer serviceContainer = new ServiceContainer(em);
        request.setAttribute("serviceContainer", serviceContainer);
        try {
            chain.doFilter(request, response);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void destroy() {

    }
}
