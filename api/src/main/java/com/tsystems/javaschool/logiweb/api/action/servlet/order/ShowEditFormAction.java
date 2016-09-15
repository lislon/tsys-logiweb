/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.servlet.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.logiweb.api.action.Action;
import com.tsystems.javaschool.logiweb.api.action.dto.BootstrapOrderDTO;
import com.tsystems.javaschool.logiweb.api.action.dto.DriverJsonDTO;
import com.tsystems.javaschool.logiweb.api.helper.RenderHelper;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.dto.OrderCargoDTO;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 9/6/16.
 */
public class ShowEditFormAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (req.getParameter("id") != null) {
            req.setAttribute("bootstrapJson", bootstrapOrder(req));
        }

        RenderHelper.renderTemplate("logiweb.order.edit", req, resp);
    }

    private String bootstrapOrder(HttpServletRequest req) throws JsonProcessingException {
        ServiceContainer managers = (ServiceContainer) req.getAttribute("serviceContainer");
        OrderManager orderManager = managers.getOrderManager();

        int id = Integer.parseInt(req.getParameter("id"));


        Collection<OrderCargoDTO> cargoCollection = orderManager.getOrderCargoes(id);
        Order order = orderManager.find(id);

        int maxPayload = orderManager.getMaxPayload(order.getWaypoints());
        int routeLength = orderManager.getRouteLength(order.getWaypoints());


        List<TruckDTO> trucksCollection = managers.getTruckManager()
                .findReadyToGoTrucks(order.getDepartureCity(), maxPayload);

        List<DriverJsonDTO> drivers = new LinkedList<DriverJsonDTO>();

        if (order.getTruck() != null) {
            int tripHours = managers.getDriverManager()
                    .calculateTripDuration(routeLength, order.getTruck().getMaxDrivers());

            drivers = managers.getDriverManager()
                    .findDriversForTrip(order.getDepartureCity().getId(),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusHours(tripHours)).stream()
                    .map(DriverJsonDTO::map)
                    .collect(Collectors.toList());
        }

        List<DriverJsonDTO> driversSelected = order.getDrivers().stream()
                .map(DriverJsonDTO::map)
                .collect(Collectors.toList());

        BootstrapOrderDTO result = new BootstrapOrderDTO(
                cargoCollection,
                trucksCollection,
                drivers,
                order.getTruck() != null ? order.getTruck().getId() : null,
                driversSelected,
                routeLength,
                maxPayload
        );

        // Save data to jackson
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(result);
    }
}
