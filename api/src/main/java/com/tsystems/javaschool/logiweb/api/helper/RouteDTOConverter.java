/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.helper;

import com.tsystems.javaschool.logiweb.api.action.dto.CargoJsonDTO;
import com.tsystems.javaschool.logiweb.api.action.dto.OrderDataDTO;
import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 9/14/16.
 */
public class RouteDTOConverter {
    public static SortedSet<OrderWaypoint> getOrderWaypoints(CityManager cityManager, OrderDataDTO order)
        throws EntityNotFoundException {
        SortedSet<OrderWaypoint> waypoints = new TreeSet<>();
        int ordinal = 0;

        for (Integer cityId : order.citiesOrder) {

            City city = cityManager.findOne(cityId);

            for (CargoJsonDTO dto : order.cargoes) {
                if (dto.srcCityId == city.getId()) {
                    waypoints.add(createWaypointFromDto(dto, cityManager, OrderWaypoint.Operation.LOAD, ordinal++));
                } else if (dto.dstCityId == city.getId()) {
                    waypoints.add(createWaypointFromDto(dto, cityManager, OrderWaypoint.Operation.UNLOAD, ordinal++));
                }
            }
        }

        return waypoints;
    }

    private static OrderWaypoint createWaypointFromDto(CargoJsonDTO dto, CityManager cityManager, OrderWaypoint.Operation operation, int ordinal) {
        Cargo cargo = new Cargo();
        cargo.setTitle(dto.title);
        cargo.setName(dto.name);
        cargo.setWeight(dto.weight);
        cargo.setStatus(Cargo.Status.PREPARED);


        OrderWaypoint waypoint = new OrderWaypoint();

        int cityId;
        if (operation == OrderWaypoint.Operation.LOAD) {
            cityId = dto.srcCityId;
        } else {
            cityId = dto.dstCityId;
        }
        waypoint.setCity(cityManager.find(cityId));
        waypoint.setCargo(cargo);
        waypoint.setOperation(operation);
        waypoint.setWaypointWeight(ordinal);
        return waypoint;
    }
}
