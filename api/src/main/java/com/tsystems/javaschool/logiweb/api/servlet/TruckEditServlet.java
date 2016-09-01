/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.servlet;

import com.tsystems.javaschool.logiweb.api.helper.RenderHelper;
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
import org.apache.tiles.request.Request;
import org.apache.tiles.request.servlet.ServletRequest;
import org.apache.tiles.request.servlet.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class TruckEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServicesFacade managersFacade = new ServicesFacade();

        // Check if we edit existing track or creating new one.
        fillRequstParamsWithTruck(request, managersFacade);

        RenderHelper.renderTemplate("logiweb.truck.edit", request, response);
    }


    /**
     * Injects truck data into a request for JSP page. Source is depends on current
     * @param request
     * @param facade
     */
    private void fillRequstParamsWithTruck(HttpServletRequest request, ServicesFacade facade) {

        if (request.getParameter("id") != null) {

            // cityId must be filled with correct cityname in both cases
            int cityId;

            if (request.getMethod().equals("POST")) {
                // copy all parameters from POST to request attributes
                Stream.of("name", "maxDrivers", "capacityKg", "cityId", "condition")
                        .forEach(param -> request.setAttribute("name", request.getParameter("name")));

                cityId = Integer.parseInt(request.getParameter("cityId"));
            } else {
                // brand new page, load truck from database

                int id = Integer.parseInt(request.getParameter("id"));
                TruckManager.DTO truck = facade.getTruckManager().find(id);


                request.setAttribute("name", truck.getName());
                request.setAttribute("maxDrivers", truck.getMaxDrivers());
                request.setAttribute("capacityKg", truck.getCapacityKg());
                request.setAttribute("cityId", truck.getCityId());
                request.setAttribute("condition", truck.getCondition());
                cityId = truck.getId();
            }

            // fill city name
            CityManager.DTO city = facade.getCityManager().find(cityId);

            // if city is deleted we still want to be able to edit truck
            if (city != null) {
                request.setAttribute("cityName", city.getName());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServicesFacade managersFacade = new ServicesFacade();
        TruckManager truckManager = managersFacade.getTruckManager();

        try {


            Integer id = null;
            if (req.getParameter("id") != null) {
                id = Integer.parseInt(req.getParameter("id"));
            }

            TruckManager.DTO truck = new TruckManager.DTO(
                    id,
                    req.getParameter("name"),
                    Integer.parseInt(req.getParameter("maxDrivers")),
                    Integer.parseInt(req.getParameter("capacityKg")),
                    Truck.Condition.valueOf(req.getParameter("condition")),
                    Integer.parseInt(req.getParameter("cityId"))
            );

            managersFacade.beginTransaction();
            try {
                truckManager.save(truck);
                managersFacade.commitTransaction();
            } catch (Exception e){
                managersFacade.rollbackTransaction();
                throw e;
            }

            UserAlert.injectInSession(req, "Successfully saved", UserAlert.Type.SUCCESS);

            resp.sendRedirect(req.getContextPath() + "/truck/list");

        } catch (Exception e) {
//            UserAlert.injectInRequest(req, "Parameter parsing error: " + e,
//                    UserAlert.Type.DANGER);
//
//            fillRequstParamsWithTruck(req, managersFacade);
//            RenderHelper.renderTemplate("logiweb.truck.edit", req, resp);
            throw e;
        }
    }
}
