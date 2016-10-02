/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.City;

import java.util.Collection;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public interface CityManager extends BaseManager<City> {

    /**
     * Return a short list of cities matching name.
     *
     * @param search Beginning of city name
     * @return List of matching cities (max 10 items)
     */
    Collection<City> getAutocompleteCities(String search);

    /**
     * Returns distance between cities in meters
     *
     * @param from First city
     * @param to Second city
     * @return
     */
    int getDistance(City from, City to);
}
