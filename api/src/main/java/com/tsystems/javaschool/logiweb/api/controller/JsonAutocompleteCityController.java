/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller;

import com.tsystems.javaschool.logiweb.api.action.dto.CityAutocompleteDTO;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 10/4/16.
 */
@RestController
@RequestMapping("/api/city/list")
public class JsonAutocompleteCityController {

    @Autowired
    private CityManager manager;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<CityAutocompleteDTO> search(@RequestParam String q) {
        Collection<City> cities = manager.getAutocompleteCities(q);

        return cities.stream()
                .map(city -> new CityAutocompleteDTO(city.getId(), city.getName()))
                .collect(Collectors.toList());
    }
}
