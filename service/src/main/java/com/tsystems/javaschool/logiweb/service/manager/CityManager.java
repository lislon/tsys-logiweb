/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.repos.CityRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class CityManager {

    public static class DTO
    {
        public final int id;
        public final String name;

        public DTO(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

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
    public Collection<DTO> getAutocompleteCities(String search) {
        Collection<City> repoAutocompleteCities = repo.getAutocompleteCities(search);

        return repoAutocompleteCities
                .stream()
                .map(city -> new DTO(city.getId(), city.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Returns city object by id.
     *
     * @param id City id.
     * @return City Entity
     */
    public DTO find(int id) {
        City city = repo.find(id);
        return new DTO(city.getId(), city.getName());
    }
}
