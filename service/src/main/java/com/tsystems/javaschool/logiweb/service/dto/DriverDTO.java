/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
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
@AllArgsConstructor
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
    @Size(min = 1, max = 10, message = "Personal code should be 1-10 long.")
    private String personalCode;

    private Driver.Status status;

    @NotNull
    @Min(0)
    private int hoursWorked;

    /**
     * Last known location of driver
     */
    @NotNull
    private Integer cityId;

    // Used only for displaying
    @NotNull(message = "Location must be set")
    private String cityName;
}
