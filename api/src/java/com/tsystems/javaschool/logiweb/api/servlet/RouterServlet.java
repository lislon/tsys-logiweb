/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.servlet;

import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.servlet.ServletRequest;
import org.apache.tiles.request.servlet.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Igor Avdeev on 8/27/16.
 */
@WebServlet(name = "RouterServlet")
public class RouterServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ApplicationContext tilesContext = ServletUtil.getApplicationContext(request.getServletContext());
//        TilesContainer container = TilesAccess.getContainer(tilesContext);
//        container.render("logiweb.truck.list", new ServletRequest(tilesContext, request, response));
        response.getWriter().write("Hi");
    }
}
