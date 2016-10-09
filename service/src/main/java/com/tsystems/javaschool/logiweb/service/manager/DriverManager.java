/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
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
     * @param cityId
     * @param dutyStart
     * @param dutyEnd
     * @return
     */
    List<Driver> findDriversForTrip(int cityId, LocalDateTime dutyStart, LocalDateTime dutyEnd);

    /**
     * Return approximation of trip duration with given number of drivers.
     *
     * @param routeLength Route length in km
     * @param numDrivers  Number of drivers in truck
     * @return double number of total hours required to trip, include rest time.
     */
    int calculateTripDuration(int routeLength, int numDrivers);

    /**
     * Adds new driver to database.
     *
     * @param driverData Driver fields
     * @throws EntityNotFoundException when driver city not found
     */
    int create(DriverDTO driverData) throws EntityNotFoundException;

    /**
     * Updates driver information in database.
     *
     * @param id Driver id
     * @param driverData Driver fields
     * @throws EntityNotFoundException when driver city not found
     */
    void update(int id, DriverDTO driverData) throws EntityNotFoundException;

    DriverDTO findDto(int id) throws EntityNotFoundException;
}
