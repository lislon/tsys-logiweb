/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
@Data
@AllArgsConstructor
public class TruckDTO {
    public final int id;
    public final String name;
    public final int maxDrivers;
    public final int capacityKg;
    public final Truck.Condition condition;
    public final int cityId;
    public final String cityName;
}
