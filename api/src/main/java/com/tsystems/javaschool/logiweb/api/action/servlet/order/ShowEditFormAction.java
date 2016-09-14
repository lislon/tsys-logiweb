/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.servlet.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.logiweb.api.action.Action;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.api.action.dto.DriverJsonDTO;
import com.tsystems.javaschool.logiweb.api.helper.RenderHelper;
import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.dto.OrderCargoDTO;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 9/6/16.
 */
public class ShowEditFormAction implements Action {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class BootstrapOrderDTO {
        public Collection<OrderCargoDTO> cargoCollection;
        public Collection<TruckDTO> trucksCollection;
        public Collection<DriverJsonDTO> driversCollection;
        public int selectedTruckId;
        public Collection<DriverJsonDTO> selectedDriversCollection;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {



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
                            LocalDateTime.now().plusHours(tripHours))
                    .stream()
                    .map(DriverJsonDTO::map)
                    .collect(Collectors.toList());
        }

        List<DriverJsonDTO> driversSelected = order.getDrivers()
                .stream()
                .map(DriverJsonDTO::map)
                .collect(Collectors.toList());

        ObjectMapper obj = new ObjectMapper();

        BootstrapOrderDTO result = new BootstrapOrderDTO(
                cargoCollection,
                trucksCollection,
                drivers,
                order.getTruck().getId(),
                driversSelected
        );

        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, result);
        req.setAttribute("bootstrapJson", stringWriter.toString());


        RenderHelper.renderTemplate("logiweb.order.edit", req, resp);
    }
}
