/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.api.order;

import com.tsystems.javaschool.logiweb.api.action.Action;
import com.tsystems.javaschool.logiweb.api.helper.ServicesFacade;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Igor Avdeev on 9/11/16.
 */
public class RouteMetaGetAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServicesFacade servicesFacade = ((ServicesFacade)req.getAttribute("servicesFacade"));

        String route = req.getParameter("route");

        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("length", 100);
        jsonBuilder.add("duration", 100);
        jsonBuilder.add("requiredCapacity", 100);

        Json.createWriter(resp.getWriter()).writeObject(jsonBuilder.build());
    }
}
