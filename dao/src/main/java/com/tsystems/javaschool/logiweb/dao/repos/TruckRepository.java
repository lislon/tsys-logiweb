/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
@Service
@Repository
public interface TruckRepository extends CrudRepository<Truck, Integer> {
    @Query("select t from Truck t " +
            "where " +
            " t.condition = :condition and " +
            " t.capacityKg >= :min_capacity and " +
            " t.city = :cityId and " +
            " not exists (from Order o where o.truck = t and o.isCompleted = false)")
    List<Truck> findReadyToGoTrucks(@Param("cityId") int cityId,
                                    @Param("maxDrivers") int maxDrivers);

    Truck findByName(String name);
}

