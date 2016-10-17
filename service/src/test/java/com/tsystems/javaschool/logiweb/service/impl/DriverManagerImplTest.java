/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.repos.DriverRepository;
import com.tsystems.javaschool.logiweb.service.LogiwebConfig;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.manager.UserManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Igor Avdeev on 9/21/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = { "classpath*:/META-INF/spring/applicationContext.xml" }),
        @ContextConfiguration("/testApplicationContext.xml")
})
public class DriverManagerImplTest {

    @Autowired
    private LogiwebConfig appConfig;

    private DriverRepository mockRepo;
    private DriverManager manager;
    private CityManager cityManager;
    private UserManager userManager;

    @Before
    public void createManager() {
        mockRepo = mock(DriverRepository.class);
        cityManager = mock(CityManager.class);
        userManager = mock(UserManager.class);
        manager = new DriverManagerImpl(mockRepo, cityManager, userManager, appConfig);
    }

    @Test
    public void findDriversForTripInsideOneMonth() throws Exception {

        LocalDateTime time1 = LocalDateTime.of(2016, 1, 1, 0, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2016, 1, 2, 1, 0, 0);
        manager.findDriversForTrip(0, time1, time2);

        verify(mockRepo).findFreeDriversInCity(0, appConfig.getMaxMonthlyDutyHours() - 25);
    }

    @Test
    public void findDriversForTripOverlapMonths() throws Exception {

        // 2016-01-31 20:00 Only 4 hours left till month end
        // 2016-02-10 01:00
        LocalDateTime time1 = LocalDateTime.of(2016, 1, 31, 20, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2016, 2, 10, 1, 0, 0);
        manager.findDriversForTrip(0, time1, time2);

        // driver should have at least 4 hours of duty time in this month
        verify(mockRepo).findFreeDriversInCity(0, appConfig.getMaxMonthlyDutyHours() - 4);
    }



    @Test
    public void canAddDriver() {

        Driver driver = new Driver();
        driver.setFirstName("John");
        driver.setLastName("Williams");
        driver.setHoursWorked(12);

        manager.save(driver);

        verify(mockRepo).save(driver);
    }
}