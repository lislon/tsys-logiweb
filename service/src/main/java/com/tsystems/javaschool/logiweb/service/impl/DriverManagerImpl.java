/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.repos.DriverRepository;
import com.tsystems.javaschool.logiweb.service.LogiwebConfig;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
import com.tsystems.javaschool.logiweb.service.dto.converter.DriverDTOConverter;
import com.tsystems.javaschool.logiweb.service.exception.DataIntegrityError;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.business.InvalidStateException;
import com.tsystems.javaschool.logiweb.service.helper.WorkingHoursCalc;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.tsystems.javaschool.logiweb.dao.entities.Driver.Status.*;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DriverManagerImpl extends BaseManagerImpl<Driver, DriverRepository>
        implements DriverManager {

    private static final Logger LOG = LoggerFactory.getLogger(DriverManagerImpl.class);

    private CityManager cityManager;
    private UserManager userManager;
    private LogiwebConfig appConfig;

    @Autowired
    public DriverManagerImpl(DriverRepository driverRepository, CityManager cityManager, UserManager userManager, LogiwebConfig appConfiguration) {
        super(driverRepository);
        this.cityManager = cityManager;
        this.userManager = userManager;
        this.appConfig = appConfiguration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDriver(int id) throws InvalidStateException, EntityNotFoundException {
        Driver d = findOneOrFail(id);
        if (d.getOrder() != null) {
            throw new InvalidStateException("Driver is assigned to order #" + d.getOrder().getId());
        }
        delete(id);
    }

    @Override
    public Order findOrderByDriverId(int driverId) throws EntityNotFoundException {
        return findOneOrFail(driverId).getOrder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Driver> findDriversForTrip(int cityId, LocalDateTime dutyStart, LocalDateTime dutyEnd) {

        int requiredWorkHours = (int) WorkingHoursCalc.getRequiredWorkHoursInCurrentMonth(dutyStart, dutyEnd);

        return repo.findFreeDriversInCity(cityId, appConfig.getMaxMonthlyDutyHours() - requiredWorkHours);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public int create(DriverDTO driverDTO) throws EntityNotFoundException {

        Driver driver = new Driver();

        DriverDTOConverter.convertToEntity(cityManager, userManager, driverDTO, driver);

        repo.saveAndFlush(driver);

        return driver.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(int id, DriverDTO driverDTO) throws EntityNotFoundException {

        Driver driver = this.findOneOrFail(id);

        DriverDTOConverter.convertToEntity(cityManager, userManager, driverDTO, driver);

        repo.save(driver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DriverDTO findDto(int id) throws EntityNotFoundException {
        return DriverDTOConverter.convertToDto(this.findOneOrFail(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DriverDTO> findAllDrivers()  {
        List<Driver> all = repo.findAll();
        List<DriverDTO> result = new LinkedList<>();
        try {
            for (Driver d : all) {
                result.add(DriverDTOConverter.convertToDto(d));
            }
            return result;
        } catch (EntityNotFoundException e) {
            LOG.error("City not found while loading list of drivers", e);
            throw new DataIntegrityError("City not found while loading list of drivers", e);
        }
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

        if (driver.getOrder() == null) {
            throw new InvalidStateException("Driver have no assigned order to change status");
        }

        if (newStatus == REST && !driver.getOrder().isCompleted()) {
            throw new InvalidStateException("Can't change status to rest, because order is not completed");
        }

        if (newStatus == DUTY_DRIVE) {
            boolean isSomebodyElseDriving = driver.getOrder().getDrivers()
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
            driver.setOrder(null);
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
