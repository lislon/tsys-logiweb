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
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;
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
        public static class CargoDTO {
            public String name;
            public int weight;
            public int srcCityId;
            public int dstCityId;
        }

        public List<CargoDTO> cargoes;
        public List<Integer> citiesOrder;
    }

    @AllArgsConstructor
    public static class TruckIdNameDTO {
        public int id;
        public String name;
        public int maxDrivers;
        public int capacity;
    }

    @AllArgsConstructor
    private static class OutRouteMetaDTO {
        public int length;
        public int requiredCapacity;
        public List<TruckIdNameDTO> trucks;
    }

    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers)
        throws IOException {

        CityManager cityManager = managers.getCityManager();
        OrderManager orderManager = managers.getOrderManager();
        TruckManager truckManager = managers.getTruckManager();

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

        int maxPayload = orderManager.getMaxPayload(waypoints);
        int routeLength = orderManager.getRouteLength(waypoints);

        List<TruckIdNameDTO> truckList = truckManager
                .findReadyToGoTrucks(cities.get(0), maxPayload)
                .stream()
                .map(truck -> new TruckIdNameDTO(
                        truck.getId(),
                        truck.getName(),
                        truck.getCapacityKg(),
                        truck.getMaxDrivers()
                ))
                .collect(Collectors.toList());

        OutRouteMetaDTO result = new OutRouteMetaDTO(routeLength, maxPayload, truckList);

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