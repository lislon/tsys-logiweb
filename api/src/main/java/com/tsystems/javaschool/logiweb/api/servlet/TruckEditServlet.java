/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.servlet;

import com.tsystems.javaschool.logiweb.api.helper.RenderHelper;
import com.tsystems.javaschool.logiweb.api.helper.ServicesFacade;
import com.tsystems.javaschool.logiweb.api.helper.UserAlert;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class TruckEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServicesFacade managersFacade = new ServicesFacade();

        // Check if we edit existing track or creating new one.
        renderEditForm(req, resp, new ServicesFacade());
    }


    /**
     * Injects truck data into a req for JSP page. Source is depends on current
     * @param req
     * @param facade
     */
    private void renderEditForm(HttpServletRequest req, HttpServletResponse resp,
                                ServicesFacade facade) {



        // cityId must be filled with correct cityname in both cases
        int cityId = 0;

        if (req.getMethod().equals("POST")) {
            // copy all parameters from POST to req attributes
            Stream.of("name", "maxDrivers", "capacityKg", "cityId", "condition")
                    .forEach(name -> req.setAttribute(name, req.getParameter(name)));

            try {
                cityId = Integer.parseInt(req.getParameter("cityId"));
            } catch (Exception e) {
            }
        } else if (req.getParameter("id") != null) {
            // brand new page, load truck from database

            int id = Integer.parseInt(req.getParameter("id"));
            TruckManager.DTO truck = facade.getTruckManager().find(id);


            req.setAttribute("name", truck.getName());
            req.setAttribute("maxDrivers", truck.getMaxDrivers());
            req.setAttribute("capacityKg", truck.getCapacityKg());
            req.setAttribute("cityId", truck.getCityId());
            req.setAttribute("condition", truck.getCondition());
            cityId = truck.getId();
        }

        if (cityId > 0) {
            // fill city name
            CityManager.DTO city = facade.getCityManager().find(cityId);

            // if city is deleted we still want to be able to edit truck
            if (city != null) {
                req.setAttribute("cityName", city.getName());
            }
        }

        RenderHelper.renderTemplate("logiweb.truck.edit", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServicesFacade managersFacade = new ServicesFacade();
        TruckManager truckManager = managersFacade.getTruckManager();

        try {

            if (!Stream.of("name", "maxDrivers", "capacityKg", "cityId", "condition")
                    .allMatch(name -> req.getParameter(name) != null
                            && req.getParameter(name).trim().length() > 0)) {
                UserAlert.injectInRequest(req, "Not all required fields are set",
                        UserAlert.Type.DANGER);

                renderEditForm(req, resp, managersFacade);
                return;
            }


            int id = 0;
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

            UserAlert.injectInSession(req, req.getParameter("name") + " truck is saved", UserAlert.Type.SUCCESS);

            resp.sendRedirect(req.getContextPath() + "/truck/list");

        } catch (Exception e) {
//            UserAlert.injectInRequest(req, "Parameter parsing error: " + e,
//                    UserAlert.Type.DANGER);
//
//            renderEditForm(req, managersFacade);
            throw e;
        }
    }
}

