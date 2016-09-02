/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action;



import com.tsystems.javaschool.logiweb.api.action.api.city.AutocompleteGetAction;
import com.tsystems.javaschool.logiweb.api.action.api.truck.ListGetAction;
import com.tsystems.javaschool.logiweb.api.action.servlet.truck.EditGetAction;
import com.tsystems.javaschool.logiweb.api.action.servlet.truck.EditPostAction;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by Igor Avdeev on 9/2/16.
 */
public class ActionFactory {

    private HashMap<String, Action> actions = new HashMap<>();

    public ActionFactory() {
        actions.put("GET /", ((req, resp) -> resp.sendRedirect(req.getContextPath() + "/truck/list.do")));

        actions.put("GET /api/truck/list.do", new ListGetAction());
        actions.put("GET /api/city/autocomplete.do", new AutocompleteGetAction());
        actions.put("GET /truck/list.do", new com.tsystems.javaschool.logiweb.api.action.servlet.truck.ListGetAction());
        actions.put("GET /truck/edit.do", new EditGetAction());
        actions.put("POST /truck/edit.do", new EditPostAction());
    }

    public Action getAction(HttpServletRequest req)
    {
        // GET [api]/[truck]/[list] -> action.[api].[city].[Autocomplete][Get][Action]
        String query = req.getRequestURI();
        query = query.substring(req.getContextPath().length());


        return actions.get(req.getMethod() + " " + query);
    }
}
