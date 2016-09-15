/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto;

import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import lombok.AllArgsConstructor;

/**
 * Created by Igor Avdeev on 9/15/16.
 */
@AllArgsConstructor
public class WaypointDTO {
    public String city;
    public OrderWaypoint.Operation operation;
    public String cargoName;
}
