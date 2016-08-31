/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.City;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class CityRepository extends BaseRepository<City> {
    public CityRepository(EntityManager em) {
        super(City.class, em);
    }

    /**
     * Return a short list of cities matching name.
     *
     * @param search Beginning of city name
     * @return List of matching cities (max 10 items)
     */
    public Collection<City> getAutocompleteCities(String search)
    {
        Query query = em.createQuery("from City c " +
                "where c.name like ? " +
                "order by c.name asc");

        query.setParameter(0, search.replace("%", "%%") + "%");
        query.setMaxResults(10);
        return query.getResultList();
    }
}
