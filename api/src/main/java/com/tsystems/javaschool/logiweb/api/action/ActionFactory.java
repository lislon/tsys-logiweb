/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action;



import com.tsystems.javaschool.logiweb.api.action.api.city.AutocompleteGetAction;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by Igor Avdeev on 9/2/16.
 */
public class ActionFactory {

    private HashMap<String, Action> actions = new HashMap<>();

    public ActionFactory() {
        actions.put("GET /", ((req, resp) -> resp.sendRedirect(req.getContextPath() + "/truck/list.do")));

        // truck
        actions.put("GET /truck/list.do",           new com.tsystems.javaschool.logiweb.api.action.servlet.truck.ListGetAction());
        actions.put("GET /truck/edit.do",           new com.tsystems.javaschool.logiweb.api.action.servlet.truck.EditGetAction());
        actions.put("POST /truck/edit.do",          new com.tsystems.javaschool.logiweb.api.action.servlet.truck.EditPostAction());
        actions.put("GET /api/truck/list.do",       new com.tsystems.javaschool.logiweb.api.action.api.truck.ListGetAction());
        actions.put("DELETE /api/truck/delete.do",  new com.tsystems.javaschool.logiweb.api.action.api.truck.EditDeleteAction());

        // city
        actions.put("GET /api/city/autocomplete.do", new AutocompleteGetAction());

        // order
        actions.put("GET /order/list.do",       new com.tsystems.javaschool.logiweb.api.action.servlet.order.ListGetAction());
        actions.put("GET /order/edit.do",       new com.tsystems.javaschool.logiweb.api.action.servlet.order.EditGetAction());
        actions.put("POST /order/edit.do",      new com.tsystems.javaschool.logiweb.api.action.servlet.order.EditPostAction());
        actions.put("GET /api/order/list.do",   new com.tsystems.javaschool.logiweb.api.action.api.order.ListGetAction());
        actions.put("GET /api/order/routeMeta.do",   new com.tsystems.javaschool.logiweb.api.action.api.order.RouteMetaGetAction());
        actions.put("GET /api/order/trucks.do",   new com.tsystems.javaschool.logiweb.api.action.api.order.ListTrucksGetAction());
        actions.put("GET /api/order/drivers.do",   new com.tsystems.javaschool.logiweb.api.action.api.order.ListDriversGetAction());
        actions.put("DELETE /order/edit.do",    new com.tsystems.javaschool.logiweb.api.action.api.order.EditDeleteAction());

        // driver
        actions.put("GET /driver/list.do",       new com.tsystems.javaschool.logiweb.api.action.servlet.driver.ListGetAction());
        actions.put("GET /driver/edit.do",       new com.tsystems.javaschool.logiweb.api.action.servlet.driver.EditGetAction());
        actions.put("POST /driver/edit.do",      new com.tsystems.javaschool.logiweb.api.action.servlet.driver.EditPostAction());
        actions.put("GET /api/driver/list.do",   new com.tsystems.javaschool.logiweb.api.action.api.driver.ListGetAction());
        actions.put("DELETE /driver/edit.do",    new com.tsystems.javaschool.logiweb.api.action.api.driver.EditDeleteAction());



//        actions.put("GET /order/list.do", new com.tsystems.javaschool.logiweb.api.action.servlet.truck.ListGetAction());
//        actions.put("GET /order/edit.do", new EditGetAction());
//        actions.put("POST /order/edit.do", new EditPostAction());

    }

    public Action getAction(HttpServletRequest req) {
        // GET [api]/[truck]/[list] -> action.[api].[city].[Autocomplete][Get][Action]
        String query = req.getRequestURI();
        query = query.substring(req.getContextPath().length());

        return actions.get(req.getMethod() + " " + query);
    }
}
