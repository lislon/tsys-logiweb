/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by Igor Avdeev on 9/15/16.
 */
@AllArgsConstructor
@Data
public class DriverAssignmentDTO {

    @AllArgsConstructor
    @Data
    public static class WaypointDTO {
        String cityName;
        OrderWaypoint.Operation operation;
        String cargoName;
    }

    int orderId;
    List<String> coDrivers;
    List<WaypointDTO> waypoints;
    String truckName;
    String routeDescription;
    String status;
}
