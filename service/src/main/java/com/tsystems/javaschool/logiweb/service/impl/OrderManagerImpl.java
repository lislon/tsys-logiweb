/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.*;
import com.tsystems.javaschool.logiweb.dao.repos.*;
import com.tsystems.javaschool.logiweb.service.dto.OrderSummaryDTO;
import com.tsystems.javaschool.logiweb.service.dto.WaypointDTO;
import com.tsystems.javaschool.logiweb.service.dto.converter.WaypointDTOConverter;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.RouteNotValidException;
import com.tsystems.javaschool.logiweb.service.helper.RouteCalculator;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Igor Avdeev on 9/3/16.
 */
@Service
@Transactional
public class OrderManagerImpl extends BaseManagerImpl<Order, OrderRepository>
        implements OrderManager {

//    private CargoRepository cargoRepository;
    @Autowired
    private OrderWaypointRepository waypointRepository;

    @Autowired
    private CargoRepository cargoRepo;

    @Autowired
    private CityRepository cityRepo;

    @Autowired
    private RouteCalculator routeCalculator;

    @Autowired
    public OrderManagerImpl(OrderRepository orderRepository) {
        super(orderRepository);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderSummaryDTO> getAllOrdersSummary() {
        List<Order> allOrdersSummary = repo.getAllOrdersSummary();

        List<OrderSummaryDTO> collect = allOrdersSummary.stream().map(order -> {

            SortedSet<OrderWaypoint> wp = order.getWaypoints();

            OrderSummaryDTO.OrderSummaryDTOBuilder builder = OrderSummaryDTO.builder()
                    .id(order.getId())
                    .dateCreated(order.getDateCreated())
                    .maxPayload(routeCalculator.getMaxPayload(wp))
                    .routeLength(routeCalculator.getRouteLength(wp))
                    .status(getOrderStatus(order));

            // Fill departure and desticantion city names when waypointsDto are present
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

    public List<WaypointDTO> getWaypoints(int orderId) {

        List<OrderWaypoint> orderWaypoints = repo.findOrderWaypoints(orderId);

        return orderWaypoints
                .stream()
                .map(WaypointDTOConverter::copyToDto)
                .collect(Collectors.toList());
//
//        Map<Integer, CargoLegDTO> cargoes = new HashMap<>();
//
//
//        for (OrderWaypoint ow : orderWaypoints) {
//            int id = ow.getCargo().getId();
//            String s = ow.toString();
//            if (!cargoes.containsKey(id)) {
//                if (!(ow.getOperation() == OrderWaypoint.Operation.LOAD)) {
//                    throw new RuntimeException("First cargo operation is not LOAD (orderId = " + orderId + ")");
//                }
//
//                cargoes.put(id, CargoLegDTO.builder()
//                        .title(ow.getCargo().getTitle())
//                        .name(ow.getCargo().getName())
//                        .ordinal(ow.getWaypointWeight())
//                        .srcCityId(ow.getCity().getId())
//                        .srcCityName(ow.getCity().getName())
//                        .weight(ow.getCargo().getWeight())
//                        .build()
//                );
//            } else {
//                cargoes.get(id).setDstCityId(ow.getCity().getId());
//                cargoes.get(id).setDstCityName(ow.getCity().getName());
//            }
//        }
//        return cargoes.values();
    }


    public Order.Status getOrderStatus(Order o) {
        if (o.isCompleted()) {
            return Order.Status.FINISHED;
        }
        if (o.getTruck() == null) {
            return Order.Status.NEW;
        }
        return Order.Status.PREPARED;
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
     * {@inheritDoc}
     */
    @Override
    public boolean delete(int id) {
        Order o = repo.findOne(id);

        if (o != null) {
            o.getWaypoints().clear();
        }
        return super.delete(id);
    }

    public int createOrder() {
        Order order = new Order();
        repo.saveAndFlush(order);
        return order.getId();
    }

    @Override
    public void updateWaypoints(int orderId, List<WaypointDTO> waypointsDto) throws EntityNotFoundException {
        SortedSet<OrderWaypoint> waypoints = new TreeSet<>();
        int counter = 0;

        Order order = findOneOrFail(orderId);

        for (WaypointDTO waypointDto : waypointsDto) {
            if (cargoRepo.findOne(waypointDto.getCargoId()) == null) {
                throw new EntityNotFoundException("Cargo at waypointDto " + waypointDto.toString() + " is not found");
            }

            OrderWaypoint waypoint = new OrderWaypoint();
            waypoint.setOperation(waypointDto.getOperation());
            waypoint.setCity(cityRepo.findOne(waypointDto.getCityId()));
            waypoint.setCargo(cargoRepo.findOne(waypointDto.getCargoId()));
            waypoint.setOrder(order);
            waypoint.setWaypointWeight(counter++);
            waypoint.setCompleted(false);

            waypoints.add(waypoint);
            waypointRepository.save(waypoint);
        }
        order.setWaypoints(waypoints);
        repo.save(order);
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Order create(SortedSet<OrderWaypoint> waypointsDto, Integer truckId, Collection<Integer> driversIds)
//            throws RouteNotValidException, EntityNotFoundException {
//
//        // validate our route
//        validateWaypoints(waypointsDto);
//
//        Order order = new Order();
//
//        // we need order.id in further operations
//        repo.save(order);
//
//        // replace same objects, that are same by equals by their references.
//        HashMap<Cargo, Cargo> uniqueCargoes = new HashMap<>();
//
//        // saves all cargoes
//        for (OrderWaypoint waypoint : waypointsDto) {
//
//            // check that we already saw this cargo (hashcode is the same)
//            Cargo cargo = waypoint.getCargo();
//
//            if (uniqueCargoes.containsKey(cargo)) {
//                // replace it with old reference
//                waypoint.setCargo(uniqueCargoes.get(cargo));
//            } else {
//                // add to list
//                uniqueCargoes.put(cargo, cargo);
//                // and assign order
//                cargo.setOrder(order);
//            }
//
//            waypoint.setOrder(order);
//            cargoRepository.save(cargo);
//        }
//
//        order.updateWaypoints(waypointsDto);
//
//        assignTruckAndDrivers(truckId, driversIds, order);
//
//        repo.save(order);
//
//        return order;
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Order> findDriverAssignments(String personalNumber) {
        return repo.findOrdersForDriver(personalNumber);
    }

    @Override
    public void markOrderCompleted(int orderId, boolean isCompleted) throws EntityNotFoundException {
        Order order = findOneOrFail(orderId);
        order.setCompleted(isCompleted);
        order.setDateCompleted(new Date());
        repo.save(order);
    }

}
