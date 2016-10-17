/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Truck data. Used for communication between service and api level.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TruckDTO {
    public Integer id;

    @NotNull
    @Pattern(regexp = "[A-Z]{2}\\d{5}")
    public String name;

    @Min(1)
    @Max(4)
    @NotNull
    // Failed to convert property value of type [java.lang.String] to required type [int] for property capacityKg; nested exception is java.lang.NumberFormatException: For input string: ""
    public int maxDrivers;

    @NotNull
    public int capacityKg;

    @NotNull
    public Truck.Condition condition;

    @NotNull
    public int cityId;

    public String cityName;
}
