package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.repos.DriverRepository;
import com.tsystems.javaschool.logiweb.service.helper.RouteCalculator;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;

public class RouteCalculatorTest {
    
    private RouteCalculator calculator;

    @Before
    public void createCalculator() {
        CityManager cityManager = mock(CityManager.class);
        calculator = new RouteCalculator(cityManager);
    }
    
    @Test
    public void calcSmallRoutes() throws Exception {
        // 80km: 1 driver = 1 hour
        assertEquals(1.0, calculator.getRouteDuration(80, 1));
        // 80km: 2 driver = 1 hour
        assertEquals(1.0, calculator.getRouteDuration(80, 2));
    }

    @Test
    public void checkDurationOfLongRouteWithOneDriver() throws Exception {
        // 1000km: 1 driver. 8 hours (drive 640 km) + 14 hours (sleep) + 4.5 hours (360 km)
        int distanceKm = 1000;
        assertEquals(28.5, calculator.getRouteDuration(distanceKm, 1));
    }

    @Test
    public void checkDurationOfLongRouteWithTwoDrivers() throws Exception {
        // 1000km: 2 drivers. 12.5 hours (drive 1000km)
        int distanceKm = 1000;
        assertEquals(12.5, calculator.getRouteDuration(distanceKm, 2));
    }
}
