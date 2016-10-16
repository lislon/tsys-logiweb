/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto;

import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Created by Igor Avdeev on 9/15/16.
 */
@AllArgsConstructor
public class DriverAssignmentDTO {

    @AllArgsConstructor
    public static class WaypointDTO {
        public String cityName;
        public OrderWaypoint.Operation operation;
        public String cargoName;
    }

    public int orderId;
    public List<String> coDrivers;
    public List<WaypointDTO> waypoints;
    public String truckName;
    public String routeDescription;
    public String status;
}
