/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.repos.CityRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class CityManager {
    private CityRepository repo = null;

    public CityManager(CityRepository repo) {
        this.repo = repo;
    }

    /**
     * Return a short list of cities matching name.
     *
     * @param search Beginning of city name
     * @return List of matching cities (max 10 items)
     */
    public Collection<City> getAutocompleteCities(String search) {
        return repo.getAutocompleteCities(search);
    }

    /**
     * Returns city object by id.
     *
     * @param id City id.
     * @return City Entity
     */
    public City find(int id) {
        return repo.find(id);
    }
}
