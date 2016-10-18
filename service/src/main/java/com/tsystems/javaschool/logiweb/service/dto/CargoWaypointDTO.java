package com.tsystems.javaschool.logiweb.service.dto;

import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CargoWaypointDTO {
    String cityName;
    OrderWaypoint.Operation operation;
    String cargoName;
    String cargoTitle;
}
