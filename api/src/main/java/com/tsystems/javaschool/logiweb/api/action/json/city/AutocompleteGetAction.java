/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.city;

import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class AutocompleteGetAction extends JsonAction {

    @AllArgsConstructor
    private static class CityDTO
    {
        public int id;
        public String name;
    }

    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers) {

        Collection<City> cities;
        String search = req.getParameter("q");

        if (search != null && search.length() >= 1) {
            cities = managers.getCityManager().getAutocompleteCities(search);
        } else {
            cities = new LinkedList<>();
        }

        List<CityDTO> list = cities.stream()
                .map(city -> new CityDTO(city.getId(), city.getName()))
                .collect(Collectors.toList());

        return JsonResult.list(list);
    }
}
