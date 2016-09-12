/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.helper.LatLngDistanceCalculator;
import com.tsystems.javaschool.logiweb.dao.repos.CityRepository;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;

import java.util.Collection;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class CityManagerImpl extends BaseManagerImpl<City, CityRepository>
    implements CityManager {


    public CityManagerImpl(CityRepository cityRepository, ServiceContainer services) {
        super(cityRepository, services);
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
     * Returns distance between cities in meters
     *
     * @param from First city
     * @param to Second city
     * @return
     */
    public int getDistance(City from, City to) {
        return (int)LatLngDistanceCalculator.distance(from.getLat(), from.getLng(),
                to.getLat(), to.getLng());
    }
}
