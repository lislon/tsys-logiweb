/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Igor Avdeev on 9/13/16.
 */
@Data
@AllArgsConstructor
public class DriverRowDTO {
    public final int id;
    public final String name;
    public final String status;
    public final int hoursWorked;
    public final int cityId;
    public final String cityName;
}
