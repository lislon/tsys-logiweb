/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.business.InvalidStateException;

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

    /**
     * Returns Driver or throws exception.
     *
     * @param id
     * @return
     * @throws EntityNotFoundException
     */
    DriverDTO findDto(int id) throws EntityNotFoundException;

    /**
     * List all drivers
     *
     * @return List of all drivers in database
     */
    List<DriverDTO> findAllDrivers();


    /**
     * Changes driver status for given order (OffDuty, OnDuty, OnDutyRest).
     *
     * @param driverId
     * @param newStatus
     */
    void changeDriverStatus(int driverId, Driver.Status newStatus) throws EntityNotFoundException, InvalidStateException;

    /**
     * Deletes a driver by his id
     * @param id Driver id
     * @throws InvalidStateException when driver is assigned to order
     * @throws EntityNotFoundException when driver not found
     */
    void deleteDriver(int id) throws InvalidStateException, EntityNotFoundException;

    Order findOrderByDriverId(int driverId) throws EntityNotFoundException;

}
