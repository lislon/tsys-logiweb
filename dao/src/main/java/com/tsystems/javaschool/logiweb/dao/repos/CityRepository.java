/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.helper.LatLngDistanceCalculator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * Created by Igor Avdeev on 8/28/16.
 */
@Service
@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    /**
     * Return a short list of cities matching name.
     *
     * @param search Beginning of city name
     * @return List of matching cities (max 10 items)
     */
    List<City> findByNameStartingWith(String search, Pageable topTen);

//    @Query("from City c where c.name like %?1 order by c.name asc limit 10")
//    Collection<City> getAutocompleteCities(String search) {
//        Query query = em.createQuery("from City c " +
//                "where c.name like :name " +
//                "order by c.name asc");
//
//        query.setParameter("name", search.replace("%", "%%") + "%");
//        query.setMaxResults(10);
//        return query.getResultList();
//    }

}
