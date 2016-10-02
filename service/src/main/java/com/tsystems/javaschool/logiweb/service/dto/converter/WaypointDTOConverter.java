package com.tsystems.javaschool.logiweb.service.dto.converter;

import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.service.dto.WaypointDTO;

public class WaypointDTOConverter {
    public static WaypointDTO copyToDto(OrderWaypoint from) {

        WaypointDTO to = new WaypointDTO();

        to.setCargoId(from.getCargo().getId());
        to.setCityId(from.getCity().getId());
        to.setOperation(from.getOperation());

        return to;
    }

}
