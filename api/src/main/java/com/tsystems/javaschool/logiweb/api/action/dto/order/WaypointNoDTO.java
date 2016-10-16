package com.tsystems.javaschool.logiweb.api.action.dto.order;

import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaypointNoDTO {
    private int cargoId;
    private int cityNo;
    private OrderWaypoint.Operation operation;
}