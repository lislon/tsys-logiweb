/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;



/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Repository
@Service
public interface DriverRepository extends CrudRepository<Driver, Integer> {

    /**
     * Searchs all drivers who are located in given city, free of duty and worked no more them `maxHours` hours in current month.
     * @param cityId City, where we search drivers
     * @param maxHours Maximum hours worked in current month.
     * @return
     */
    @Query("from Driver d " +
            " where d.city.id = :cityId and d.status = 'RES' and d.hoursWorked < :maxHours" +
            " and not exists (from Order o join o.drivers od where od = d and o.isCompleted = false )")
    List<Driver> findFreeDriversInCity(@Param("cityId") int cityId, @Param("maxHours") int maxHours);

}
