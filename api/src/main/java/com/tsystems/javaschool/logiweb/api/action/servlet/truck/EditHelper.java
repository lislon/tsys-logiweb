/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.servlet.truck;

import com.tsystems.javaschool.logiweb.api.helper.RenderHelper;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

/**
 * Created by Igor Avdeev on 9/2/16.
 */
public class EditHelper {
    /**
     * Injects truck data into a req for JSP page. Source is depends on current
     * @param req
     * @param facade
     */
    public static void renderEditForm(HttpServletRequest req, HttpServletResponse resp,
                                ServiceContainer facade) {

        // cityId must be filled with correct cityname in both cases
        int cityId = 0;

        if (req.getMethod().equals("POST")) {
            // copy all parameters from POST to req attributes
            Stream.of("name", "maxDrivers", "capacityKg", "cityId", "condition")
                    .forEach(name -> req.setAttribute(name, req.getParameter(name)));

            try {
                cityId = Integer.parseInt(req.getParameter("cityId"));
            } catch (Exception ignored) {
                // cityId = 0
            }
        } else if (req.getParameter("id") != null) {
            // brand new page, load truck from database

            int id = Integer.parseInt(req.getParameter("id"));
            Truck truck = facade.getTruckManager().find(id);


            req.setAttribute("name", truck.getName());
            req.setAttribute("maxDrivers", truck.getMaxDrivers());
            req.setAttribute("capacityKg", truck.getCapacityKg());
            req.setAttribute("cityId", truck.getCity().getId());
            req.setAttribute("condition", truck.getCondition());
            cityId = truck.getId();
        }

        if (cityId > 0) {
            // fill city name
            City city = facade.getCityManager().find(cityId);

            // if city is deleted we still want to be able to edit truck
            if (city != null) {
                req.setAttribute("cityName", city.getName());
            }
        }

        RenderHelper.renderTemplate("logiweb.truck.edit", req, resp);
    }
}
