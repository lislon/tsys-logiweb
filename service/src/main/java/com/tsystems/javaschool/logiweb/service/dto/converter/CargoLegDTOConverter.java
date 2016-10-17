package com.tsystems.javaschool.logiweb.service.dto.converter;

import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.service.dto.CargoLegDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CargoLegDTOConverter {

    public static Collection<CargoLegDTO> retrieveCargoLegs(Order order) {
        Map<Integer, CargoLegDTO> cargoLegs = new HashMap<>();
        for (OrderWaypoint waypoint : order.getWaypoints()) {

            if (waypoint.getOperation() == OrderWaypoint.Operation.LOAD) {

                CargoLegDTO leg = new CargoLegDTO();
                leg.setName(waypoint.getCargo().getName());
                leg.setTitle(waypoint.getCargo().getTitle());
                leg.setWeight(waypoint.getCargo().getWeight());
                leg.setSrcCityId(waypoint.getCity().getId());
                leg.setSrcCityName(waypoint.getCity().getName());

                cargoLegs.put(waypoint.getCargo().getId(), leg);
            } else {
                CargoLegDTO leg = cargoLegs.get(waypoint.getCargo().getId());
                leg.setDstCityId(waypoint.getCity().getId());
                leg.setDstCityName(waypoint.getCity().getName());
            }
        }
        return cargoLegs.values();
    }
}
