/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto;

import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Created by Igor Avdeev on 9/15/16.
 */
@AllArgsConstructor
public class DriverAssignmentDTO {
    public int orderId;
    public List<String> coDrivers;
    public List<WaypointDTO> waypoints;
    public String truckName;
    public String routeDescription;
}
