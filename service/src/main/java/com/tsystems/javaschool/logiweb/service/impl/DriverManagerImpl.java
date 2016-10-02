/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.repos.DriverRepository;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
@Service
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

    private CityManager cityManager;

    @Autowired
    public DriverManagerImpl(DriverRepository driverRepository, CityManager cityManager) {
        super(driverRepository);
        this.cityManager = cityManager;
    }

    /**<context:component-scan base-package="com.tsystems.javaschool.logiweb.service" /><context:component-scan base-package="com.tsystems.javaschool.logiweb.service" />
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

        return repo.findFreeDriversInCity(cityId, Driver.MONTH_DUTY_HOURS - (int)requiredWorkHours);
    }

    /**
     * Return aproximation of trip duration with given number of drivers.
     *
     * @param routeLength Route length in km
     * @param numDrivers  Number of drivers in truck
     * @return double number of total hours required to do trip, include rest time.
     */
    public int calculateTripDuration(Integer routeLength, Integer numDrivers) {
        double distancePerDay = (Math.min(numDrivers * LIMIT_HOURS_DAY_DRIVE, 24) * AVG_TRUCK_SPEED);

        double hours = Math.floor(routeLength / distancePerDay) * 24 + (routeLength % distancePerDay) / AVG_TRUCK_SPEED;

        return (int)Math.ceil(hours);
    }

    @Override
    public void save(Driver driver, Integer cityId, Integer truckId)
            throws EntityNotFoundException {

        City city = cityManager.findOneOrDie(cityId);
        driver.setCity(city);
        if (driver.getId() > 0) {
            repo.save(driver);
        } else {
            repo.save(driver);
        }
    }
}
