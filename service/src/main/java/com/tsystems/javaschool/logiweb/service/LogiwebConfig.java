/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LogiwebConfig {

    @Value("${app.maxWorkingHours}")
    private int maxDriverWorkingHours;

    @Value("${app.limitHoursPerDay}")
    private int limitHoursPerDay;

    @Value("${app.truckAvgSpeed}")
    private int truckAvgSpeed;

    public int getLimitHoursPerDay() {
        return limitHoursPerDay;
    }

    public int getMaxMonthlyDutyHours() {
        return maxDriverWorkingHours;
    }

    public int getTruckAvgSpeed() {
        return truckAvgSpeed;
    }

    public void setTruckAvgSpeed(int truckAvgSpeed) {
        this.truckAvgSpeed = truckAvgSpeed;
    }
}
