/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.servlet;

import com.tsystems.javaschool.logiweb.api.helper.ServicesFacade;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.json.Json;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class ApiTruckListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TruckManager manager = (new ServicesFacade()).getTruckManager();

        List<Truck> allTrucks = manager.findAllTrucks();

        JsonArrayBuilder jsonBuilder = Json.createArrayBuilder();

        for (Truck truck : allTrucks) {
            JsonObjectBuilder truckJson = Json.createObjectBuilder()
                    .add("id", truck.getId())
                    .add("name", truck.getName())
                    .add("max_drivers", truck.getMaxDrivers())
                    .add("capacity_kg", truck.getCapacityKg())
                    .add("condition", truck.getCondition().toString())
                    .add("city_name", truck.getCity().getName());

            jsonBuilder.add(truckJson);
        }

        Json.createWriter(response.getWriter()).writeArray(jsonBuilder.build());
    }
}
