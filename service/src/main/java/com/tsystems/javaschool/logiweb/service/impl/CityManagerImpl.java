/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.helper.LatLngDistanceCalculator;
import com.tsystems.javaschool.logiweb.dao.repos.CityRepository;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
@Service
public class CityManagerImpl extends BaseManagerImpl<City, CityRepository> implements CityManager {

    @Autowired
    public CityManagerImpl(CityRepository cityRepository) {
        super(cityRepository);
    }

    /**
     * Return a short list of cities matching name.
     *
     * @param search Beginning of city name
     * @return List of matching cities (max 10 items)
     */
    public Collection<City> getAutocompleteCities(String search) {
        if (search.length() > 1) {
            Pageable topTen = new PageRequest(0, 10);
            return repo.findByNameStartingWith(search, topTen);
        } else {
            return Collections.emptyList();
        }
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
