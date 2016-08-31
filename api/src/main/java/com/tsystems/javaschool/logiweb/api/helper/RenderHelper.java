/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.helper;

import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.servlet.ServletRequest;
import org.apache.tiles.request.servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Igor Avdeev on 9/1/16.
 */
public class RenderHelper {
    public static void renderTemplate(String name, HttpServletRequest request, HttpServletResponse response) {
        ApplicationContext tilesContext = ServletUtil.getApplicationContext(request.getServletContext());
        TilesContainer container = TilesAccess.getContainer(tilesContext);
        container.render(name, new ServletRequest(tilesContext, request, response));
    }
}
