/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.repos.DriverRepository;
import com.tsystems.javaschool.logiweb.service.impl.DriverManagerImpl;
import org.junit.Before;
import org.junit.Test;

import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Igor Avdeev on 9/11/16.
 */
public class DriverManagerTest {

    DriverRepository mockRepo;
    DriverManager manager;
    ServiceContainer container;

    @Before
    public void createManager() {
        mockRepo = mock(DriverRepository.class);
        container = mock(ServiceContainer.class);
        manager = new DriverManagerImpl(mockRepo, container);
    }

    @Test
    public void findDriversForTripInsideOneMonth() throws Exception {

        LocalDateTime time1 = LocalDateTime.of(2016, 1, 1, 0, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2016, 1, 2, 1, 0, 0);
        manager.findDriversForTrip(0, time1, time2);

        verify(mockRepo).findFreeDriversInCity(0, 25);
    }

    @Test
    public void findDriversForTripOverlapMonths() throws Exception {

        LocalDateTime time1 = LocalDateTime.of(2016, 1, 31, 20, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2016, 2, 10, 1, 0, 0);
        manager.findDriversForTrip(0, time1, time2);

        verify(mockRepo).findFreeDriversInCity(0, 4);
    }


    @Test
    public void calcSmallRoutes() throws Exception {
        // 80km: 1 driver = 1 hour
        assertEquals(1.0, manager.calculateTripDuration(80, 1), 1e-15);
        // 80km: 2 driver = 1 hour
        assertEquals(1.0, manager.calculateTripDuration(80, 2), 1e-15);
    }

    @Test
    public void calcSingleDriverLongRoute() throws Exception {
        // 1000km: 1 driver. 8 hours (drive 640 km) + 14 hours (sleep) + 4.5 hours (360 km)
        assertEquals(28.5, manager.calculateTripDuration(1000, 1), 1e-15);
    }

    @Test
    public void calcTwoDriversLongRoute() throws Exception {
        // 1000km: 2 drivers. 12.5 hours (drive 1000km)
        assertEquals(12.5, manager.calculateTripDuration(1000, 2), 1e-15);
    }

    @Test
    public void canAddDriver() {

        Driver driver = new Driver();
        driver.setFirstName("John");
        driver.setLastName("Williams");
        driver.setHoursWorked(12);

        manager.save(driver);

        verify(mockRepo).create(driver);
    }

}