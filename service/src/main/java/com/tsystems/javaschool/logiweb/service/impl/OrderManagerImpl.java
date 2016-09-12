/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.dao.repos.OrderRepository;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.dto.OrderSummaryDTO;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Igor Avdeev on 9/3/16.
 */
public class OrderManagerImpl extends BaseManagerImpl<Order, OrderRepository>
        implements OrderManager {

    public OrderManagerImpl(OrderRepository orderRepository, ServiceContainer services) {
        super(orderRepository, services);
    }

    @Override
    public List<OrderSummaryDTO> getAllOrdersSummary() {
        List<Order> allOrdersSummary = repo.getAllOrdersSummary();

        List<OrderSummaryDTO> collect = allOrdersSummary.stream().map(order -> {
            OrderSummaryDTO.OrderSummaryDTOBuilder builder = OrderSummaryDTO.builder()
                    .id(order.getId())
                    .cityStartId(order.getWaypoints().first().getCity().getId())
                    .cityStartName(order.getWaypoints().first().getCity().getName())
                    .cityEndId(order.getWaypoints().last().getCity().getId())
                    .cityEndName(order.getWaypoints().last().getCity().getName())
                    .dateCreated(order.getDateCreated())
                    .maxPayload(getMaxPayload(order.getWaypoints()))
                    .routeLength(getRouteLength(order.getWaypoints()))
                    .status(getOrderStatus(order));

            if (order.getTruck() != null) {
                builder.truckName(order.getTruck().getName())
                       .truckId(order.getTruck().getId());
            }



            return builder.build();
        }).collect(Collectors.toList());
        return collect;
    }

    private OrderSummaryDTO.Status getOrderStatus(Order o) {
        if (o.isCompleted()) {
            return OrderSummaryDTO.Status.FINISHED;
        }
        if (o.getTruck() == null) {
            return OrderSummaryDTO.Status.NEW;
        }
        return OrderSummaryDTO.Status.PREPARED;
    }

    /**
     * Return a route length in KM
     *
     * @param waypointCollection
     * @return route length in KM
     */
    @Override
    public int getRouteLength(Collection<OrderWaypoint> waypointCollection) {

        if (waypointCollection.size() <= 1) {
            return 0;
        }
        Iterator<OrderWaypoint> iterator = waypointCollection.iterator();
        City lastCity = iterator.next().getCity();
        int totalDistance = 0;

        while (iterator.hasNext()) {
            City thisCity = iterator.next().getCity();
            if (!thisCity.equals(lastCity)) {
                totalDistance += services.getCityManager().getDistance(
                        lastCity,
                        thisCity
                );
            }
            lastCity = thisCity;
        }
        return totalDistance;
    }


    /**
     * Returns maximum summary carried weight for segment along the route.
     *
     * @param waypointCollection List of waypoints for given route
     * @return Maximum weight carried for single segment (in kg)
     */
    @Override
    public int getMaxPayload(Collection<OrderWaypoint> waypointCollection) {
        int maxPayload = 0;
        Set<Cargo> cargoOnBoard = new HashSet<>();
        for (OrderWaypoint p : waypointCollection) {
            if (p.getOperation() == OrderWaypoint.Operation.LOAD) {
                cargoOnBoard.add(p.getCargo());
            } else if (p.getOperation() == OrderWaypoint.Operation.UNLOAD) {
                cargoOnBoard.remove(p.getCargo());
            }
            maxPayload = Math.max(
                    cargoOnBoard.stream().mapToInt(Cargo::getWeight).sum(),
                    maxPayload);
        }
        return maxPayload;
    }

}
