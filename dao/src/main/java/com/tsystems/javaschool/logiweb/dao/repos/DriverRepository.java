/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


/**
 * Created by Igor Avdeev on 8/23/16.
 */
public class DriverRepository extends BaseRepository<Driver> {

    public DriverRepository(EntityManager manager) {
        super(Driver.class, manager);
    }

    /**
     * Searchs a driver with free status in given city, which have less then `maxHoursWorked` worked.
     * @param city City, where we search drivers
     * @param hoursRemaining
     * @return
     */
    public List<Driver> findFreeDriversInCity(City city, int hoursRemaining) {
        Query query = em.createQuery("from Driver d where d.city = :city and d.status = :status and d.hoursWorked < :maxHoursWorked");

        query.setParameter("city", city);
        query.setParameter("status", Driver.Status.REST);
        query.setParameter("maxHoursWorked", Driver.MONTH_DUTY_HOURS - hoursRemaining);

        return (List<Driver>)query.getResultList();
    }

}
