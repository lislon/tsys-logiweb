/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Created by Igor Avdeev on 9/14/16.
 */
@AllArgsConstructor
@NoArgsConstructor
public class DriverJsonDTO {
    public int id;
    public String name;
    public int workedHours;

    public static DriverJsonDTO map(Driver driver) {
        DriverJsonDTO that = new DriverJsonDTO();
        that.id = driver.getId();
        that.name = driver.getFirstName() + " " + driver.getLastName();
        that.workedHours = driver.getHoursWorked();
        return that;
    }
}
