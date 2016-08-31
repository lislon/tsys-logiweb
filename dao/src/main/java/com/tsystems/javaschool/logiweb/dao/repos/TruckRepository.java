/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public class TruckRepository extends BaseRepository<Truck> {
    public TruckRepository(EntityManager em) {
        super(Truck.class, em);
    }

    public List<Truck> findReadyToGoTrucks(City city, int minCapacity)
    {
        return em.createQuery("select t from Truck t " +
                "where t.condition = :condition and t.capacityKg >= :min_capacity and t.city = :city")
                .setParameter("condition", Truck.Condition.OK)
                .setParameter("min_capacity", minCapacity)
                .setParameter("city", city)
                .getResultList();
    }
}
