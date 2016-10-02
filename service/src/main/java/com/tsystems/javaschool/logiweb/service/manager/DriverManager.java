/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public interface DriverManager extends BaseManager<Driver> {

    /**
     * Find unassigned drivers in city `departure` available to work from now till tillDate.
     *
     * @param cityId City in which we search drivers.
     * @param dutyEnd
     * @return
     */
    List<Driver> findDriversForTrip(Integer cityId, LocalDateTime dutyStart, LocalDateTime dutyEnd);

    /**
     * Return approximation of trip duration with given number of drivers.
     *
     * @param routeLength Route length in km
     * @param numDrivers  Number of drivers in truck
     * @return double number of total hours required to do trip, include rest time.
     */
    int calculateTripDuration(Integer routeLength, Integer numDrivers);

    void save(Driver entity, Integer cityId, Integer truckId)
            throws EntityNotFoundException;
}
