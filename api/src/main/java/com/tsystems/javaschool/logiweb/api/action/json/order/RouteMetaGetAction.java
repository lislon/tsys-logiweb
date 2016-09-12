/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Igor Avdeev on 9/11/16.
 */
public class RouteMetaGetAction extends JsonAction {

    private static class InRouteDTO {
        static class CargoDTO {
            String name;
            int weight;
            int srcCityId;
            int dstCityId;
        }

        List<Integer> citiesOrder;
        List<CargoDTO> cargoes;
    }

    @AllArgsConstructor
    private static class OutRouteMetaDTO {
        int length;
        int requiredCapacity;
    }

    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers)
        throws IOException {

        CityManager cityManager = managers.getCityManager();
        OrderManager orderManager = managers.getOrderManager();

        ObjectMapper mapper = new ObjectMapper();


        // Convert JSON string from file to Object
        InRouteDTO route = mapper.readValue(req.getParameter("route"), InRouteDTO.class);


        List<City> cities = route.citiesOrder.stream()
                .map(cityManager::find)
                .collect(Collectors.toList());

        List<OrderWaypoint> waypoints = new LinkedList<>();

        for (Integer cityId : route.citiesOrder) {

            City city = cityManager.find(cityId);

            List<OrderWaypoint> listLoad = route.cargoes
                    .stream()
                    .filter(cargo -> cargo.srcCityId == city.getId())
                    .map((dto -> this.createWaypointFromDto(dto, cityManager, OrderWaypoint.Operation.LOAD)))
                    .collect(Collectors.toList());

            waypoints.addAll(listLoad);

            List<OrderWaypoint> listUnLoad = route.cargoes
                    .stream()
                    .filter(cargo -> cargo.dstCityId == city.getId())
                    .map((dto -> this.createWaypointFromDto(dto, cityManager, OrderWaypoint.Operation.UNLOAD)))
                    .collect(Collectors.toList());


            waypoints.addAll(listUnLoad);

        }

        OutRouteMetaDTO result = new OutRouteMetaDTO(
                orderManager.getRouteLength(waypoints),
                orderManager.getMaxPayload(waypoints)
        );

        return JsonResult.object(result);
    }

    private OrderWaypoint createWaypointFromDto(InRouteDTO.CargoDTO dto, CityManager cityManager, OrderWaypoint.Operation operation) {
        Cargo cargo = new Cargo();
        cargo.setTitle(dto.name);
        cargo.setStatus(Cargo.Status.PREPARED);
        cargo.setWeight(dto.weight);

        OrderWaypoint waypoint = new OrderWaypoint();

        if (operation == OrderWaypoint.Operation.LOAD) {
            waypoint.setCity(cityManager.find(dto.srcCityId));
        } else {
            waypoint.setCity(cityManager.find(dto.dstCityId));
        }
        waypoint.setCargo(cargo);
        waypoint.setOperation(operation);
        return waypoint;
    }
}
