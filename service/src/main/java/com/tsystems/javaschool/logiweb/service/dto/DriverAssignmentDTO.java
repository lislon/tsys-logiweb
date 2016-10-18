/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by Igor Avdeev on 9/15/16.
 */
@NoArgsConstructor
@Data
public class DriverAssignmentDTO {

    int orderId;
    List<DriverDTO> coDrivers;
    Map<String, Map<OrderWaypoint.Operation, List<CargoWaypointDTO>>> cityXOperationXWaypoints;
    TruckDTO truck;
    String truckLicencePlate;
    String routeDescription;
    String status;
    String srcCityName;
    String dstCityName;
    int routeDistance;
    double routeDuration;
}
