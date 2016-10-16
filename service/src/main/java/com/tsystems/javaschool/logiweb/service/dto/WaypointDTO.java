/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaypointDTO {
    private int cargoId;
    private int cityId;
    private OrderWaypoint.Operation operation;
}
