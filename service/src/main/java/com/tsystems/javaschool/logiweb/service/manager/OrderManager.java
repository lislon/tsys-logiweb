/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.service.dto.OrderSummaryDTO;
import com.tsystems.javaschool.logiweb.service.dto.OrderCargoDTO;

import java.util.*;


/**
 * Created by Igor Avdeev on 9/3/16.
 */
public interface OrderManager extends BaseManager<Order> {

    List<OrderSummaryDTO> getAllOrdersSummary();

    Collection<OrderCargoDTO> getOrderCargoes(int id);

    /**
     * Return a route length in KM
     *
     * @param waypointCollection
     * @return route length in KM
     */
    int getRouteLength(Collection<OrderWaypoint> waypointCollection);


    /**
     * Returns maximum summary carried weight for segment along the route.
     *
     * @param waypointCollection List of waypoints for given route
     * @return Maximum weight carried for single segment (in kg)
     */
    int getMaxPayload(Collection<OrderWaypoint> waypointCollection);

}
