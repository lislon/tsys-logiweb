/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
@Service
@Repository
public interface TruckRepository extends JpaRepository<Truck, Integer> {
    @Query("select t from Truck t" +
            " where " +
            " t.condition = 'OK' and" +
            " t.capacityKg >= :minCapacity and" +
            " t.city.id = :cityId and " +
            " not exists (from Order o where o.truck = t and o.isCompleted = false)")
    List<Truck> findReadyToGoTrucks(@Param("cityId") int cityId,
                                    @Param("minCapacity") int minCapacity);

    @Query("select t from Truck t where t.name = ?1 and t.id != ?2")
    Truck findByNameButNotWithId(String name, Integer notId);
}

