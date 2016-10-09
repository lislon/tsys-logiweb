/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action;


import com.tsystems.javaschool.logiweb.api.action.json.assignment.ListAssignmentsAction;
import com.tsystems.javaschool.logiweb.api.action.json.city.AutocompleteGetAction;
import com.tsystems.javaschool.logiweb.api.action.json.driver.DriverListAction;
import com.tsystems.javaschool.logiweb.api.action.json.order.RouteMetaGetAction;
import com.tsystems.javaschool.logiweb.api.action.json.truck.TruckDeleteAction;
import com.tsystems.javaschool.logiweb.api.action.json.truck.TruckListAction;
import com.tsystems.javaschool.logiweb.api.action.servlet.order.ListOrdersAction;
import com.tsystems.javaschool.logiweb.api.action.servlet.order.ShowEditFormAction;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by Igor Avdeev on 9/2/16.
 */
public class ActionFactory {

    private HashMap<String, Action> actions = new HashMap<>();

    public ActionFactory() {
        actions.put("GET /", ((req, resp) -> resp.sendRedirect(req.getContextPath() + "/truck/list")));

        // truck
        actions.put("GET /truck/list",           new com.tsystems.javaschool.logiweb.api.action.servlet.truck.ListGetAction());
        actions.put("GET /truck/edit",           new com.tsystems.javaschool.logiweb.api.action.servlet.truck.EditGetAction());
        actions.put("POST /truck/edit",          new com.tsystems.javaschool.logiweb.api.action.servlet.truck.EditPostAction());
        actions.put("GET /api/truck/list",       new TruckListAction());
        actions.put("DELETE /api/truck/delete",  new TruckDeleteAction());

        // city
        actions.put("GET /api/city/autocomplete", new AutocompleteGetAction());

        // order
        actions.put("GET /order/list",          new ListOrdersAction());
        actions.put("GET /order/edit",          new ShowEditFormAction());

        actions.put("GET /api/order/cargoes",   new com.tsystems.javaschool.logiweb.api.action.json.order.OrderGetCargoesAction());
        actions.put("GET /api/order/list",      new com.tsystems.javaschool.logiweb.api.action.json.order.OrderListAction());
        actions.put("GET /api/order/routeMeta", new RouteMetaGetAction());
        actions.put("GET /api/order/drivers",   new com.tsystems.javaschool.logiweb.api.action.json.order.OrderListAvailableDriversAction());
        actions.put("POST /api/order/edit",     new com.tsystems.javaschool.logiweb.api.action.json.order.OrderSaveAction());
        actions.put("DELETE /api/order/delete", new com.tsystems.javaschool.logiweb.api.action.json.order.OrderDeleteAction());


        // driver
        actions.put("GET /driver/list",          new com.tsystems.javaschool.logiweb.api.action.servlet.driver.ListGetAction());
        actions.put("GET /api/driver/list",      new DriverListAction());
        actions.put("DELETE /api/driver/edit",   new com.tsystems.javaschool.logiweb.api.action.json.driver.DriverDeleteAction());

        // assignment
        actions.put("GET /assignment/list",      new com.tsystems.javaschool.logiweb.api.action.servlet.assignment.ShowFormAssignmentsAction());
        actions.put("GET /api/assignment/list",  new ListAssignmentsAction());

        // cargoes
        actions.put("GET /cargo/list",    new com.tsystems.javaschool.logiweb.api.action.servlet.assignment.ShowFormAssignmentsAction());
    }

    public Action getAction(HttpServletRequest req) {
        // GET [api]/[truck]/[list] -> action.[api].[city].[Autocomplete][Get][Action]
        String query = req.getRequestURI();
        query = query.substring(req.getContextPath().length());

        return actions.get(req.getMethod() + " " + query);
    }
}
