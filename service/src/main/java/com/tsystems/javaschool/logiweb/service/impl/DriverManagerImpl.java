/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.repos.DriverRepository;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public class DriverManagerImpl extends BaseManagerImpl<Driver, DriverRepository>
        implements DriverManager {
    /**
     * How many hours driver can work per day.
     */
    private static final int LIMIT_HOURS_DAY_DRIVE = 8;
    /**
     * Average truck speed (km/h)
     */
    private static final int AVG_TRUCK_SPEED = 80;

    public DriverManagerImpl(DriverRepository driverRepository, ServiceContainer services) {
        super(driverRepository, services);
    }

    /**
     * Find unassigned drivers in city `departure` available to work from now till tillDate.
     *
     * @param cityId City in which we search drivers.
     * @param dutyEnd
     * @return
     */
    public List<Driver> findDriversForTrip(Integer cityId, LocalDateTime dutyStart, LocalDateTime dutyEnd) {

        long requiredWorkHours;

        if (dutyStart.getMonthValue() == dutyEnd.getMonthValue()) {
            requiredWorkHours = ChronoUnit.HOURS.between(dutyStart, dutyEnd);
        } else {
            // In case a trip passes the month border, we just care about current month
            // Let's see how many hours left till month end
            LocalDateTime endOfMonth =  LocalDateTime.of(
                    dutyStart.getYear(),
                    dutyStart.getMonth().plus(1),
                    1,
                    0, 0, 0);
            requiredWorkHours = ChronoUnit.HOURS.between(dutyStart, endOfMonth);
        }

        return repo.findFreeDriversInCity(cityId, (int)requiredWorkHours);
    }

    /**
     * Return aproximation of trip duration with given number of drivers.
     *
     * @param routeLength Route length in km
     * @param numDrivers  Number of drivers in truck
     * @return double number of total hours required to do trip, include rest time.
     */
    public int calculateTripDuration(int routeLength, int numDrivers) {
        double distancePerDay = (Math.min(numDrivers * LIMIT_HOURS_DAY_DRIVE, 24) * AVG_TRUCK_SPEED);

        double hours = Math.floor(routeLength / distancePerDay) * 24 + (routeLength % distancePerDay) / AVG_TRUCK_SPEED;

        return (int)Math.ceil(hours);
    }

    @Override
    public void save(Driver driver, int cityId, Integer truckId)
            throws EntityNotFoundException {

        City city = services.getCityManager().findOne(cityId);
        driver.setCity(city);
        if (driver.getId() > 0) {
            repo.update(driver);
        } else {
            repo.create(driver);
        }
    }
}
