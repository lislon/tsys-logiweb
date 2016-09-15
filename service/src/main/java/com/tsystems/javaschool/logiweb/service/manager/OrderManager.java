/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.service.dto.OrderSummaryDTO;
import com.tsystems.javaschool.logiweb.service.dto.OrderCargoDTO;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.RouteNotValidException;

import java.util.*;


/**
 * Created by Igor Avdeev on 9/3/16.
 */
public interface OrderManager extends BaseManager<Order> {

    /**
     * Returns collection of all orders in database with their statuses.
     *
     * @return Collection of orders with additional data. (See {OrderSummaryDTO})
     */
    List<OrderSummaryDTO> getAllOrdersSummary();

    /**
     * Return collection of cargoes of this order
     *
     * @param orderId Order identifier
     * @return
     */
    Collection<OrderCargoDTO> getOrderCargoes(int orderId);

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

    /**
     * Creates a new order. Also saves associated waypoints and cargoes.
     *
     * @param waypoints Order waypoints
     * @param truckId Assigned truck to this order or null
     * @param driversIds List of drivers assigned to this order. Can be empty
     * @return Brand-new order
     *
     * @throws RouteNotValidException when route is not valid.
     * @throws EntityNotFoundException when City, Truck or Driver associated with order is not found.
     */
    Order create(SortedSet<OrderWaypoint> waypoints, Integer truckId, Collection<Integer> driversIds)
            throws RouteNotValidException, EntityNotFoundException;

    Collection<Order> findDriverAssignments(String personalNumber);

    /**
     * Update selectedTruckId and selectedDrivers for Order
     *
     * @param orderId
     * @param selectedTruckId
     * @param selectedDrivers
     */
    void update(int orderId, Integer selectedTruckId, List<Integer> selectedDrivers) throws EntityNotFoundException;
}
