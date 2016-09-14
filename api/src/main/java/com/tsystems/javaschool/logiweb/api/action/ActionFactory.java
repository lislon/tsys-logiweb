/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action;



import com.tsystems.javaschool.logiweb.api.action.json.city.AutocompleteGetAction;
import com.tsystems.javaschool.logiweb.api.action.json.driver.DriverListAction;
import com.tsystems.javaschool.logiweb.api.action.json.order.OrderDeleteAction;
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
        actions.put("GET /", ((req, resp) -> resp.sendRedirect(req.getContextPath() + "/truck/list.do")));

        // truck
        actions.put("GET /truck/list.do",           new com.tsystems.javaschool.logiweb.api.action.servlet.truck.ListGetAction());
        actions.put("GET /truck/edit.do",           new com.tsystems.javaschool.logiweb.api.action.servlet.truck.EditGetAction());
        actions.put("POST /truck/edit.do",          new com.tsystems.javaschool.logiweb.api.action.servlet.truck.EditPostAction());
        actions.put("GET /api/truck/list.do",       new TruckListAction());
        actions.put("DELETE /api/truck/delete.do",  new TruckDeleteAction());

        // city
        actions.put("GET /api/city/autocomplete.do", new AutocompleteGetAction());

        // order
        actions.put("GET /order/list.do",           new ListOrdersAction());
        actions.put("GET /order/edit.do",           new ShowEditFormAction());
        actions.put("DELETE /order/edit.do",        new OrderDeleteAction());

        actions.put("GET /api/order/cargoes.do",   new com.tsystems.javaschool.logiweb.api.action.json.order.OrderGetCargoesAction());
        actions.put("GET /api/order/list.do",      new com.tsystems.javaschool.logiweb.api.action.json.order.OrderListAction());
        actions.put("GET /api/order/routeMeta.do", new com.tsystems.javaschool.logiweb.api.action.json.order.RouteMetaGetAction());
        actions.put("GET /api/order/drivers.do",   new com.tsystems.javaschool.logiweb.api.action.json.order.OrderListAvailableDriversAction());


        // driver
        actions.put("GET /driver/list.do",       new com.tsystems.javaschool.logiweb.api.action.servlet.driver.ListGetAction());
        actions.put("GET /api/driver/list.do",   new DriverListAction());
        actions.put("POST /api/driver/edit.do",  new com.tsystems.javaschool.logiweb.api.action.json.driver.DriverUpdateAction());
        actions.put("DELETE /api/driver/edit.do",  new com.tsystems.javaschool.logiweb.api.action.json.driver.DriverDeleteAction());


//        actions.put("GET /order/list.do", new com.tsystems.javaschool.logiweb.api.action.servlet.truck.DriverListAction());
//        actions.put("GET /order/edit.do", new DriverFormEditAction());

    }

    public Action getAction(HttpServletRequest req) {
        // GET [api]/[truck]/[list] -> action.[api].[city].[Autocomplete][Get][Action]
        String query = req.getRequestURI();
        query = query.substring(req.getContextPath().length());

        return actions.get(req.getMethod() + " " + query);
    }
}
