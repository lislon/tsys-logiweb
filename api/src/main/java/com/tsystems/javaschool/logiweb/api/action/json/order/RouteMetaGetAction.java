/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.api.action.dto.OrderDataDTO;
import com.tsystems.javaschool.logiweb.api.action.dto.RouteMetaResponseDTO;
import com.tsystems.javaschool.logiweb.api.action.dto.TruckJsonDTO;
import com.tsystems.javaschool.logiweb.api.helper.RouteDTOConverter;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.service.exception.BusinessLogicException;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Igor Avdeev on 9/11/16.
 */
public class RouteMetaGetAction extends JsonAction {

    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers)
        throws IOException {

        try {
            CityManager cityManager = managers.getCityManager();
            OrderManager orderManager = managers.getOrderManager();
            TruckManager truckManager = managers.getTruckManager();

            ObjectMapper mapper = new ObjectMapper();
            // Convert JSON string from file to Object
            OrderDataDTO route = mapper.readValue(req.getParameter("route"), OrderDataDTO.class);


            List<City> cities = route.citiesOrder.stream()
                    .map(cityManager::find)
                    .collect(Collectors.toList());


            Collection<OrderWaypoint> waypoints = RouteDTOConverter.getOrderWaypoints(cityManager, route);

            int maxPayload = orderManager.getMaxPayload(waypoints);
            int routeLength = orderManager.getRouteLength(waypoints);

            List<TruckJsonDTO> truckList = truckManager
                    .findReadyToGoTrucks(cities.get(0), maxPayload)
                    .stream()
                    .map(truck -> new TruckJsonDTO(
                            truck.getId(),
                            truck.getName(),
                            truck.getMaxDrivers(),
                            (int)Math.floor(truck.getCapacityKg() / 1000)
                    ))
                    .collect(Collectors.toList());


            RouteMetaResponseDTO result = new RouteMetaResponseDTO(
                    routeLength, maxPayload, truckList);

            return JsonResult.object(result);
        } catch (BusinessLogicException e) {
            return JsonResult.error("Invalid request " + e.getMessage());
        }
    }

}
