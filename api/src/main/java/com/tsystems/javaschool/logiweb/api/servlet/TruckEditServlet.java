/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.servlet;

import com.tsystems.javaschool.logiweb.api.helper.ServicesFacade;
import com.tsystems.javaschool.logiweb.api.helper.UserAlert;
import com.tsystems.javaschool.logiweb.api.helper.Validator;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.servlet.ServletRequest;
import org.apache.tiles.request.servlet.ServletUtil;
import org.springframework.http.HttpRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class TruckEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ApplicationContext tilesContext = ServletUtil.getApplicationContext(request.getServletContext());
        TilesContainer container = TilesAccess.getContainer(tilesContext);


        ServicesFacade managersFacade = new ServicesFacade();
        TruckManager truckManager = managersFacade.getTruckManager();
        Truck truck;

        // Check if we edit existing track or creating new one.
        if (request.getParameter("id") != null) {
            truck = truckManager.find(Integer.parseInt(request.getParameter("id")));
            // TODO: Handle not found situation with message: Sorry, specified truck was not found in database.
        } else {
            truck = new Truck();
        }
        request.setAttribute("truck", truck);

        container.render("logiweb.truck.edit", new ServletRequest(tilesContext, request, response));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ServicesFacade managersFacade = new ServicesFacade();
            TruckManager truckManager = managersFacade.getTruckManager();

            Truck truck;

            // Check if we edit existing track or creating new one.
            if (req.getParameter("id") != null) {
                truck = truckManager.find(Integer.parseInt(req.getParameter("id")));
                // TODO: Handle not found situation with message: Sorry, specified truck was not found in database.
            } else {
                truck = new Truck();
            }

            if (fillTruck(truck, req, managersFacade)) {
                truckManager.save(truck);
            }
//
//            truck.setName(req.getParameter("name"));
//            truck.setCapacityKg((int)(Double.parseDouble(req.getParameter("capacity_ton")) * 1000));


//            City c = cityManager.find(1);
            // TODO: Handle not found situation with message: Sorry, specified city was not found in database.

            UserAlert.injectInRequest(req, "Hello after save!");

        } catch (Exception e) {
            UserAlert.injectInRequest(req, e.getMessage(), UserAlert.Type.DANGER);
        }

        ApplicationContext tilesContext = ServletUtil.getApplicationContext(req.getServletContext());
        TilesContainer container = TilesAccess.getContainer(tilesContext);
        container.render("logiweb.truck.edit", new ServletRequest(tilesContext, req, resp));
    }

    private static boolean fillTruck(Truck truck, HttpServletRequest req, ServicesFacade managersFacade)
    {
        CityManager cityManager = managersFacade.getCityManager();

        HashMap<String, Class<?>> paramsToValidate = new HashMap<>();
        paramsToValidate.put("name", String.class);
        paramsToValidate.put("max_drivers", Integer.class);
        paramsToValidate.put("capacity", Integer.class);
        paramsToValidate.put("condition", Truck.Condition.class);
        paramsToValidate.put("city_id", Integer.class);
        Validator validator = new Validator(paramsToValidate, req);

        Collection<Validator.Constraint> constraints = validator.validate();

        if (validator.isValid()) {
            truck.setName(validator.getString("name"));
            truck.setMaxDrivers(validator.getInteger("max_drivers"));
            truck.setCapacityKg(validator.getInteger("capacity"));
            truck.setCondition(validator.getEnum("condition", Truck.Condition.class));

            City c = cityManager.find(validator.getInteger("city_id"));
            if (c == null) {
                // TODO: Handle not found situation with message: Sorry, specified city was not found in database.
            }

            truck.setCity(c);
            return true;
        } else {
            req.setAttribute("constraints", constraints);

        }
        return false;
    }

}
