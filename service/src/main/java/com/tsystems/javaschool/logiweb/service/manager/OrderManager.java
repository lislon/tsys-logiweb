/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.service.dto.OrderSummaryDTO;
import com.tsystems.javaschool.logiweb.service.dto.WaypointDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.business.RouteNotValidException;

import java.util.Collection;
import java.util.List;


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
//
//    /**
//     * Return collection of cargoes of this order
//     *
//     * @param orderId Order identifier
//     * @return
//     */
//    Collection<CargoLegDTO> getOrderCargoes(Integer orderId);

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
//    Order create(SortedSet<OrderWaypoint> waypoints, Integer truckId, Collection<Integer> driversIds)
//            throws RouteNotValidException, EntityNotFoundException;

    /**
     * Returns a list of orders where given driver is involved.
     *
     * @param personalNumber Personal driver number
     * @return
     */
    Collection<Order> findDriverAssignments(String personalNumber);


    /**
     * Updates completed order status.
     *
     * @param orderId Id of order
     * @param isCompleted true if order is done, false otherwise.
     */
    void markOrderCompleted(int orderId, boolean isCompleted) throws EntityNotFoundException;

    void updateWaypoints(int orderId, List<WaypointDTO> waypointsDto) throws EntityNotFoundException;

    int createOrder();

    Order.Status getOrderStatus(Order o);

}
