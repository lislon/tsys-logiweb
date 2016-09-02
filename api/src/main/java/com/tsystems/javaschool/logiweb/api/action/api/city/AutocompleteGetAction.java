/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.api.city;

import com.tsystems.javaschool.logiweb.api.action.Action;
import com.tsystems.javaschool.logiweb.api.helper.ServicesFacade;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class AutocompleteGetAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CityManager cityManager = ((ServicesFacade)req.getAttribute("servicesFacade")).getCityManager();
        Collection<CityManager.DTO> cities;
        String search = req.getParameter("q");

        if (search != null && search.length() >= 1) {
            cities = cityManager.getAutocompleteCities(search);
        } else {
            cities = new LinkedList<>();
        }

        JsonArrayBuilder jsonBuilder = Json.createArrayBuilder();

        for (CityManager.DTO city : cities) {
            JsonObjectBuilder truckJson = Json.createObjectBuilder()
                    .add("id", city.getId())
                    .add("name", city.getName());

            jsonBuilder.add(truckJson);
        }

        Json.createWriter(resp.getWriter()).writeArray(jsonBuilder.build());
    }
}
