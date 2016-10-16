/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.logiweb.api.action.dto.BootstrapOrderDTO;
import com.tsystems.javaschool.logiweb.api.action.dto.driver.DriverJsonDTO;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.dto.converter.CargoLegDTOConverter;
import com.tsystems.javaschool.logiweb.service.dto.converter.TruckDTOConverter;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.helper.RouteCalculator;
import com.tsystems.javaschool.logiweb.service.manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

//import com.tsystems.javaschool.logiweb.service.dto.OrderDTO;

/**
 * Created by Igor Avdeev on 10/4/16.
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderManager manager;

    @Autowired
    private TruckManager truckManager;

    @Autowired
    private DriverManager driverManager;

    @Autowired
    private CityManager cityManager;

    @Autowired
    private CargoManager cargoManager;


    @Autowired
    private RouteCalculator routeCalculator;

    @GetMapping
    public String list() {
        return "order.list";
    }

    @GetMapping("/new")
    public String create() {
        return "order.edit";
    }



    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model modelUi)
            throws EntityNotFoundException, JsonProcessingException {
        BootstrapOrderDTO bootstrapOrder = bootstrapOrder(id);
        modelUi.addAttribute("bootstrapJson", new ObjectMapper().writeValueAsString(bootstrapOrder));
        return "order.edit";
    }

    private BootstrapOrderDTO bootstrapOrder(int id) throws EntityNotFoundException {
        // TODO [optimization] Preload order data
        Order order = manager.findOneOrFail(id);

        int maxPayload = routeCalculator.getMaxPayload(order.getWaypoints());
        int routeLength = routeCalculator.getRouteLength(order.getWaypoints());

        List<TruckDTO> trucksCollection = truckManager
                .findReadyToGoTrucks(order.getDepartureCity().getId(), maxPayload);

        List<DriverJsonDTO> driversAvail = new LinkedList<>();

        if (order.getTruck() != null) {

            // add current truck to truck list collection
            trucksCollection.add(TruckDTOConverter.copyToDto(order.getTruck()));

            double tripHours = routeCalculator.getRouteDuration(routeLength, order.getTruck().getMaxDrivers());

            driversAvail = driverManager
                    .findDriversForTrip(
                            order.getDepartureCity().getId(),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusHours((long)tripHours))
                    .stream()
                    .map(DriverJsonDTO::map)
                    .collect(Collectors.toList());

            // add current drivers to list of available drivers
            if (!order.getDrivers().isEmpty()) {
                driversAvail.addAll(
                        order.getDrivers().stream()
                                .map(DriverJsonDTO::map)
                                .collect(Collectors.toList())
                );
            }

        }

        List<DriverJsonDTO> driversSelected = order.getDrivers().stream()
                .map(DriverJsonDTO::map)
                .collect(Collectors.toList());

        BootstrapOrderDTO result = new BootstrapOrderDTO(
                CargoLegDTOConverter.toCargoLegs(order),
                trucksCollection,
                driversAvail,
                order.getTruck() != null ? order.getTruck().getId() : null,
                driversSelected,
                routeLength,
                maxPayload
        );
        return result;
    }

}
