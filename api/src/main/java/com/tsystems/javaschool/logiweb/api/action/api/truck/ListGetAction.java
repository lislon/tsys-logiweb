/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.api.truck;

import com.tsystems.javaschool.logiweb.api.action.Action;
import com.tsystems.javaschool.logiweb.api.helper.ServicesFacade;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
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
public class ListGetAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServicesFacade servicesFacade = new ServicesFacade();

        TruckManager manager = servicesFacade.getTruckManager();
        CityManager cityManager = servicesFacade.getCityManager();

        List<TruckManager.DTO> allTrucks = manager.findAllTrucks();

        JsonArrayBuilder jsonBuilder = Json.createArrayBuilder();

        for (TruckManager.DTO truck : allTrucks) {
            JsonObjectBuilder truckJson = Json.createObjectBuilder()
                    .add("id", truck.getId())
                    .add("name", truck.getName())
                    .add("maxDrivers", truck.getMaxDrivers())
                    .add("capacityKg", truck.getCapacityKg())
                    .add("condition", truck.getCondition().toString())
                    .add("cityName", cityManager.find(truck.getId()).getName());

            jsonBuilder.add(truckJson);
        }

        Json.createWriter(resp.getWriter()).writeArray(jsonBuilder.build());

    }
}
