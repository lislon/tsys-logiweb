/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api;

import com.tsystems.javaschool.logiweb.api.action.Action;
import com.tsystems.javaschool.logiweb.api.action.ActionFactory;
import com.tsystems.javaschool.logiweb.dao.helper.LocalEntityManagerFactory;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class RouteServlet extends HttpServlet {
    private ActionFactory factory = new ActionFactory();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Action action = factory.getAction(req);

        EntityManager em = null;

        try {
            if (action != null) {

                em = LocalEntityManagerFactory.createEntityManager();
                req.setAttribute("serviceContainer", new ServiceContainer(em));

                action.execute(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
