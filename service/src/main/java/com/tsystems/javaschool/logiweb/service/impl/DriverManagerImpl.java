/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.repos.DriverRepository;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
import com.tsystems.javaschool.logiweb.service.dto.converter.DriverDTOConverter;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.InvalidStateException;
import com.tsystems.javaschool.logiweb.service.helper.WorkingHoursCalc;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static com.tsystems.javaschool.logiweb.dao.entities.Driver.Status.DUTY_DRIVE;
import static com.tsystems.javaschool.logiweb.dao.entities.Driver.Status.DUTY_REST;
import static com.tsystems.javaschool.logiweb.dao.entities.Driver.Status.REST;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
@Service
@Transactional
public class DriverManagerImpl extends BaseManagerImpl<Driver, DriverRepository>
        implements DriverManager {

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

        int requiredWorkHours = (int) WorkingHoursCalc.getRequiredWorkHoursInCurrentMonth(dutyStart, dutyEnd);

        return repo.findFreeDriversInCity(cityId, Driver.MONTH_DUTY_HOURS - requiredWorkHours);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public int create(DriverDTO driverDTO) throws EntityNotFoundException {

        Driver driver = new Driver();

        DriverDTOConverter.convertToEntity(cityManager, driverDTO, driver);

        repo.saveAndFlush(driver);

        return driver.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(int id, DriverDTO driverDTO) throws EntityNotFoundException {

        Driver driver = this.findOneOrFail(id);

        DriverDTOConverter.convertToEntity(cityManager, driverDTO, driver);

        repo.save(driver);
    }

    @Override
    public DriverDTO findDto(int id) throws EntityNotFoundException {

        return DriverDTOConverter.convertToDto(this.findOneOrFail(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeDriverStatus(int driverId, Driver.Status newStatus) throws EntityNotFoundException, InvalidStateException {

        Driver driver = findOneOrFail(driverId);

        if (driver.getStatus() == newStatus) {
            return;
        }

        if (driver.getCurrentOrder() == null) {
            throw new InvalidStateException("Driver have no assigned order to change status");
        }

        if (newStatus == REST && !driver.getCurrentOrder().isCompleted()) {
            throw new InvalidStateException("Can't change status to rest, because order is not completed");
        }

        if (newStatus == DUTY_DRIVE) {
            boolean isSomebodyElseDriving = driver.getCurrentOrder().getDrivers()
                    .stream()
                    .filter(d -> d != driver)
                    .anyMatch(d -> d.getStatus() == DUTY_DRIVE);
            if (isSomebodyElseDriving) {
                throw new InvalidStateException("Can't change status to driving, because other driver is already driving");
            }
        }

        updateDutyHours(newStatus, driver);


        // assign driver from order
        if (newStatus == REST) {
            driver.setCurrentOrder(null);
        }

        driver.setStatus(newStatus);

        repo.save(driver);
    }

    private void updateDutyHours(Driver.Status newStatus, Driver driver) {
        if (newStatus == DUTY_DRIVE || newStatus == DUTY_REST) {
            if (driver.getLastDutySince() == null) {
                driver.setLastDutySince(new Date());
            }
        } else {
            LocalDateTime dutyStart = LocalDateTime.ofInstant(
                    driver.getLastDutySince().toInstant(),
                    ZoneId.systemDefault());

            double hoursWorked = dutyStart.until(LocalDateTime.now(), ChronoUnit.HOURS);
            driver.setHoursWorked(driver.getHoursWorked() + (int)Math.ceil(hoursWorked));
            driver.setLastDutySince(null);
        }
    }

}
