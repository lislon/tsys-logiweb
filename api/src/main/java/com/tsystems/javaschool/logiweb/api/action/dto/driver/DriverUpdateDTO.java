/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto.driver;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import lombok.AllArgsConstructor;

/**
 * Created by Igor Avdeev on 10/4/16.
 */

@AllArgsConstructor
public class DriverUpdateDTO {
    public String firstName;
    public String lastName;
    public String personalCode;
    public int hoursWorked;
    public Driver.Status status;
    public int cityId;
}
