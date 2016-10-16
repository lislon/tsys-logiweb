/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller.rest;

import com.tsystems.javaschool.logiweb.api.action.dto.CityAutocompleteDTO;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 10/4/16.
 */
@RestController
@RequestMapping("/cities")
public class CityRestController {

    @Autowired
    private CityManager manager;

    @GetMapping("/api/autocomplete")
    public List<CityAutocompleteDTO> autocomplete(@RequestParam String q) {
        Collection<City> cities = manager.getAutocompleteCities(q);

        return cities.stream()
                .map(city -> new CityAutocompleteDTO(city.getId(), city.getName()))
                .collect(Collectors.toList());
    }
}
