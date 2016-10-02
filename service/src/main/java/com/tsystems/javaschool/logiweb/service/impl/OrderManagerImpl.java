/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.*;
import com.tsystems.javaschool.logiweb.dao.repos.CargoRepository;
import com.tsystems.javaschool.logiweb.dao.repos.OrderRepository;
import com.tsystems.javaschool.logiweb.dao.repos.OrderWaypointRepository;
import com.tsystems.javaschool.logiweb.service.dto.OrderSummaryDTO;
import com.tsystems.javaschool.logiweb.service.dto.OrderCargoDTO;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.RouteNotValidException;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Igor Avdeev on 9/3/16.
 */
@Service
public class OrderManagerImpl extends BaseManagerImpl<Order, OrderRepository>
        implements OrderManager {

    private CargoRepository cargoRepository;
    private OrderWaypointRepository waypointRepository;
    private CityManager cityManager;
    private DriverManager driverManager;
    private TruckManager truckManager;

    @Autowired
    public OrderManagerImpl(OrderRepository orderRepository, CargoRepository cargoRepository, OrderWaypointRepository waypointRepository, CityManager cityManager, DriverManager driverManager, TruckManager truckManager) {
        super(orderRepository);
        this.cargoRepository = cargoRepository;
        this.waypointRepository = waypointRepository;
        this.cityManager = cityManager;
        this.driverManager = driverManager;
        this.truckManager = truckManager;
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

            // Fill departure and desticantion city names when waypoints are present
            if (wp.size() > 0) {
                builder.cityStartId(wp.first().getCity().getId())
                       .cityStartName(wp.first().getCity().getName())
                       .cityEndId(wp.last().getCity().getId())
                       .cityEndName(wp.last().getCity().getName());
            }

            // Fill truck name if it's assigned.
            if (order.getTruck() != null) {
                builder.truckName(order.getTruck().getName())
                       .truckId(order.getTruck().getId());
            }

            return builder.build();
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public Collection<OrderCargoDTO> getOrderCargoes(Integer orderId) {
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
    public Integer getRouteLength(Collection<OrderWaypoint> waypointCollection) {

        if (waypointCollection.size() <= 1) {
            return 0;
        }
        Iterator<OrderWaypoint> iterator = waypointCollection.iterator();
        City lastCity = iterator.next().getCity();
        int totalDistance = 0;

        while (iterator.hasNext()) {
            City thisCity = iterator.next().getCity();
            if (!thisCity.equals(lastCity)) {
                totalDistance += cityManager.getDistance( lastCity, thisCity );
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
    public Integer getMaxPayload(Collection<OrderWaypoint> waypointCollection) {
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
    public boolean delete(Integer id) {
        Order o = repo.findOne(id);

        if (o != null) {
            o.getWaypoints().clear();
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
        repo.save(order);

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
            cargoRepository.save(cargo);
        }

        order.setWaypoints(waypoints);

        assignTruckAndDrivers(truckId, driversIds, order);

        repo.save(order);

        return order;
    }

    @Override
    public Collection<Order> findDriverAssignments(String personalNumber) {
        Collection<Order> ordersForDriver = repo.findOrdersForDriver(personalNumber);
        return ordersForDriver;
    }

    @Override
    public void update(Integer orderId, Integer selectedTruckId, List<Integer> selectedDrivers) throws EntityNotFoundException {
        Order o = findOneOrDie(orderId);
        assignTruckAndDrivers(selectedTruckId, selectedDrivers, o);
        repo.save(o);
    }

    private void assignTruckAndDrivers(Integer truckId, Collection<Integer> driversIds, Order order) throws EntityNotFoundException {
        order.setTruck(truckId == null ? null : truckManager.findOneOrDie(truckId));

        Set<Driver> drivers = new HashSet<>();
        for (Integer driverId : driversIds) {
            drivers.add(driverManager.findOneOrDie(driverId));
        }

        order.setDrivers(drivers);
    }
}
