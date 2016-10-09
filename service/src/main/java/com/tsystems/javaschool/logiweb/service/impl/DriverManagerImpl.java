/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.repos.DriverRepository;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import org.modelmapper.ModelMapper;
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

    /**
     * Find unassigned drivers in city `departure` available to work from now till tillDate.
     *
     * @param cityId City in which we search drivers.
     * @param dutyEnd
     * @return
     */
    public List<Driver> findDriversForTrip(int cityId, LocalDateTime dutyStart, LocalDateTime dutyEnd) {

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
     * @return double number of total hours required to trip, include rest time.
     */
    public int calculateTripDuration(int routeLength, int numDrivers) {
        double distancePerDay = (Math.min(numDrivers * LIMIT_HOURS_DAY_DRIVE, 24) * AVG_TRUCK_SPEED);

        double hours = Math.floor(routeLength / distancePerDay) * 24 + (routeLength % distancePerDay) / AVG_TRUCK_SPEED;

        return (int)Math.ceil(hours);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int create(DriverDTO driverDTO) throws EntityNotFoundException {

        Driver driver = new Driver();

        convertToEntity(driverDTO, driver);

        repo.save(driver);

        return driver.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(int id, DriverDTO driverDTO) throws EntityNotFoundException {

        Driver driver = this.findOneOrFail(id);

        convertToEntity(driverDTO, driver);

        repo.save(driver);
    }

    @Override
    public DriverDTO findDto(int id) throws EntityNotFoundException {

        return convertToDto(this.findOneOrFail(id));

    }

    private DriverDTO convertToDto(Driver from) throws EntityNotFoundException {

        DriverDTO to = new DriverDTO();

        to.setId(from.getId());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setStatus(from.getStatus());
        to.setHoursWorked(from.getHoursWorked());
        to.setPersonalCode(from.getPersonalCode());
        if (from.getCity() != null) {
            to.setCityId(from.getCity().getId());
            to.setCityName(from.getCity().getName());
        }
        return to;
    }

    private void convertToEntity(DriverDTO from, Driver to) throws EntityNotFoundException {

        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setStatus(from.getStatus());
        to.setHoursWorked(from.getHoursWorked());
        to.setPersonalCode(from.getPersonalCode());

        if (from.getCityId() != null) {
            to.setCity(cityManager.findOneOrFail(from.getCityId()));
        }
    }
}
