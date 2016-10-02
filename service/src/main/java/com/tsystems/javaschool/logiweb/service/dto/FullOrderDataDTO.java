/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import lombok.Data;

import java.util.List;

/**
 * DTO representing order information before creation
 */
@Data
public class FullOrderDataDTO {

    @Data
    public static class WaypointNoDTO {
        /**
         * Index of cargo in cargoes array
         */
        private int cargoNo;
        private int cityId;
        private OrderWaypoint.Operation operation;
    }

    List<CargoDTO> cargoes;
    List<WaypointNoDTO> waypoints;

    Integer selectedTruckId;
    List<Integer> selectedDrivers;
}
