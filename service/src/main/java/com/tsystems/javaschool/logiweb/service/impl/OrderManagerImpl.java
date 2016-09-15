/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.*;
import com.tsystems.javaschool.logiweb.dao.repos.CargoRepository;
import com.tsystems.javaschool.logiweb.dao.repos.OrderRepository;
import com.tsystems.javaschool.logiweb.dao.repos.OrderWaypointRepository;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.dto.OrderSummaryDTO;
import com.tsystems.javaschool.logiweb.service.dto.OrderCargoDTO;
import com.tsystems.javaschool.logiweb.service.exception.BusinessLogicException;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.RouteNotValidException;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Igor Avdeev on 9/3/16.
 */
public class OrderManagerImpl extends BaseManagerImpl<Order, OrderRepository>
        implements OrderManager {

    private CargoRepository cargoRepository;
    private OrderWaypointRepository waypointRepository;

    public OrderManagerImpl(OrderRepository orderRepository,
                            CargoRepository cargoRepository,
                            OrderWaypointRepository waypointRepository,
                            ServiceContainer services) {
        super(orderRepository, services);
        this.cargoRepository = cargoRepository;
        this.waypointRepository = waypointRepository;
    }

    @Override
    public List<OrderSummaryDTO> getAllOrdersSummary() {
        List<Order> allOrdersSummary = repo.getAllOrdersSummary();

        List<OrderSummaryDTO> collect = allOrdersSummary.stream().map(order -> {

            SortedSet<OrderWaypoint> wp = order.getWaypoints();

            OrderSummaryDTO.OrderSummaryDTOBuilder builder = OrderSummaryDTO.builder()
                    .id(order.getId())
                    .dateCreated(order.getDateCreated())
                    .maxPayload(getMaxPayload(wp))
                    .routeLength(getRouteLength(wp))
                    .status(getOrderStatus(order));

            if (wp.size() > 0) {
                builder.cityStartId(wp.first().getCity().getId())
                       .cityStartName(wp.first().getCity().getName())
                       .cityEndId(wp.last().getCity().getId())
                       .cityEndName(wp.last().getCity().getName());
            }

            if (order.getTruck() != null) {
                builder.truckName(order.getTruck().getName())
                       .truckId(order.getTruck().getId());
            }

            return builder.build();
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public Collection<OrderCargoDTO> getOrderCargoes(int orderId) {
        Map<Integer, OrderCargoDTO> cargoes = new HashMap<>();
        List<OrderWaypoint> orderWaypoints = repo.findOrderWaypoints(orderId);

        for (OrderWaypoint ow : orderWaypoints) {
            int id = ow.getCargo().getId();
            String s = ow.toString();
            if (!cargoes.containsKey(id)) {
                if (!(ow.getOperation() == OrderWaypoint.Operation.LOAD)) {
                    throw new RuntimeException("First cargo operation is not LOAD (orderId = " + orderId + ")");
                }

                cargoes.put(id, OrderCargoDTO.builder()
                        .title(ow.getCargo().getTitle())
                        .name(ow.getCargo().getName())
                        .ordinal(ow.getWaypointWeight())
                        .srcCityId(ow.getCity().getId())
                        .srcCityName(ow.getCity().getName())
                        .weight(ow.getCargo().getWeight())
                        .build()
                );
            } else {
                cargoes.get(id).setDstCityId(ow.getCity().getId());
                cargoes.get(id).setDstCityName(ow.getCity().getName());
            }
        }
        return cargoes.values();
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
     * @param waypointCollection Waypoints
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
     * Validates that all cargoes is loaded somewhere and unloaded somewhere.
     *
     * @param waypointCollection Waypoints
     * @throws RouteNotValidException if some cargo is not loaded or unloaded
     */
    private void validateWaypoints(Collection<OrderWaypoint> waypointCollection) throws RouteNotValidException {
        Set<Cargo> cargoOnBoard = new HashSet<>();
        for (OrderWaypoint p : waypointCollection) {
            if (p.getOperation() == OrderWaypoint.Operation.LOAD) {
                cargoOnBoard.add(p.getCargo());
            } else if (p.getOperation() == OrderWaypoint.Operation.UNLOAD) {
                if (!cargoOnBoard.contains(p.getCargo())) {
                    throw new RouteNotValidException("Cargo " + p + " is not unloaded, but not loaded before");
                }
                cargoOnBoard.remove(p.getCargo());
            }
        }
        if (!cargoOnBoard.isEmpty()) {
            String message = "Cargo is not unloaded: " + cargoOnBoard.iterator().next();
            throw new RouteNotValidException(message);
        }
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

    @Override
    public boolean delete(int id) {
        Order o = repo.find(id);



        if (o != null) {
            o.getWaypoints().clear();
//            for (OrderWaypoint w : o.getWaypoints()) {
////                OrderWaypoint w2 = waypointRepository.find(w.getId());
//                waypointRepository.delete(w.getId());
//            }
//            o.setWaypoints(new TreeSet<>());
        }
        return super.delete(id);
    }

    @Override
    public Order create(SortedSet<OrderWaypoint> waypoints, Integer truckId, Collection<Integer> driversIds)
            throws RouteNotValidException, EntityNotFoundException {

        // validate our route
        validateWaypoints(waypoints);

        Order order = new Order();

        // we need order.id in further operations
        repo.create(order);

        // replace same objects, that are same by equals by their references.
        HashMap<Cargo, Cargo> uniqueCargoes = new HashMap<>();

        // saves all cargoes
        for (OrderWaypoint waypoint : waypoints) {

            // check that we already saw this cargo (hashcode is the same)
            Cargo cargo = waypoint.getCargo();

            if (uniqueCargoes.containsKey(cargo)) {
                // replace it with old reference
                waypoint.setCargo(uniqueCargoes.get(cargo));
            } else {
                // add to list
                uniqueCargoes.put(cargo, cargo);
                // and assign order
                cargo.setOrder(order);
            }

            waypoint.setOrder(order);
            cargoRepository.create(cargo);
        }

        order.setWaypoints(waypoints);

        order.setTruck(truckId == null ? null : services.getTruckManager().findOne(truckId));

        Set<Driver> drivers = new HashSet<>();
        DriverManager driverManager = services.getDriverManager();
        for (Integer driverId : driversIds) {
            drivers.add(driverManager.findOne(driverId));
        }

        order.setDrivers(drivers);

        repo.update(order);

        return order;
    }
}
