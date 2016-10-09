/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Igor Avdeev on 9/13/16.
 */
@Data
@NoArgsConstructor
public class DriverDTO {

    private Integer id;

    @NotNull
    @Size(min = 1, max = 256, message = "Name should be 1-256 chars long.")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 256, message = "Name should be 1-256 chars long.")
    private String lastName;

    @NotNull
    @Size(min = 1, max = 10, message = "Personal code should be 1-10 chars long.")
    private String personalCode;

    private Driver.Status status;

    @NotNull
    @Min(0)
    private int hoursWorked;

    /**
     * Location of driver. Can be null if it's unknown.
     */
    private Integer cityId;

    // Used only for displaying
    private String cityName;

}
