/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.city;

import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class AutocompleteGetAction extends JsonAction {

    final static Logger logger = LoggerFactory.getLogger(AutocompleteGetAction.class);

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
            logger.debug("autocomplete request: " + search);
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
