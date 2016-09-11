/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.models;


import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.repos.DriverRepository;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public class DriverManagerTest {

    @Test
    public void canAddDriver()
    {
        DriverRepository mockRepo = mock(DriverRepository.class);
        DriverManager manager = new DriverManager(mockRepo);

        Driver driver = new Driver();
        driver.setFirstName("John");
        driver.setLastName("Williams");
        driver.setHoursWorked(12);

        manager.addDriver(driver);

        verify(mockRepo).save(driver);
    }
}