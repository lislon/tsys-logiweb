/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.dao.repos.CargoRepository;
import com.tsystems.javaschool.logiweb.dao.repos.OrderRepository;
import com.tsystems.javaschool.logiweb.dao.repos.OrderWaypointRepository;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint.Operation.LOAD;
import static com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint.Operation.UNLOAD;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Igor Avdeev on 9/21/16.
 */
//public class OrderManagerImplTest {
//
//    OrderRepository mockRepo;
//    CargoRepository cargoRepo;
//    OrderWaypointRepository waypointRepo;
//    OrderManagerImpl manager;
//    CityManager cityManager;
//    private TruckManager truckManager;
//    private DriverManager driverManager;
//
//    @Before
//    public void createManager() {
//        mockRepo = mock(OrderRepository.class);
//        cargoRepo = mock(CargoRepository.class);
//        waypointRepo = mock(OrderWaypointRepository.class);
//        cityManager = mock(CityManager.class);
//        truckManager = mock(TruckManager.class);
//        driverManager = mock(DriverManager.class);
//        manager = new OrderManagerImpl(mockRepo, cargoRepo, waypointRepo, driverManager, truckManager);
//    }
//
//    @Test
//    public void getRouteLength() throws Exception {
//        // use real CityManager to calculate distances
//        City moscow = new City();
//        moscow.setName("Moscow");
//
//        City spb = new City();
//        spb.setName("Spb");
//
//        City munich = new City();
//        munich.setName("Munich");
//
//        Cargo cargoAB = createCargo(10);
//        Cargo cargoBC = createCargo(15);
//
//        // let
//        // moscow ->  spb 600 km
//        // spb -> munich = 2000 km
//        when(cityManager.getDistance(moscow, spb)).thenReturn(600);
//        when(cityManager.getDistance(spb, munich)).thenReturn(2000);
//
//        List<OrderWaypoint> route = Arrays.asList(
//                createWaypoint(moscow, cargoAB, LOAD),
//                createWaypoint(spb, cargoAB, UNLOAD),
//                createWaypoint(spb, cargoBC, LOAD),
//                createWaypoint(munich, cargoBC, UNLOAD)
//        );
//
//        int routeLength = manager.getRouteLength(route);
//        // then total distance should be 2000 + 600
//        assertEquals(2600, routeLength);
//    }
//
//    @Test
//    public void getMaxPayload() throws Exception {
//
//        Cargo cargoAB = createCargo(10);
//        Cargo cargoAC = createCargo(10);
//        Cargo cargoBC = createCargo(15);
//
//        City cityA = createCity();
//        City cityB = createCity();
//        City cityC = createCity();
//
//        List<OrderWaypoint> list = Arrays.asList(
//                createWaypoint(cityA, cargoAB, LOAD),
//                createWaypoint(cityA, cargoAC, LOAD),
//                createWaypoint(cityB, cargoAB, UNLOAD),
//                createWaypoint(cityB, cargoBC, LOAD),
//                createWaypoint(cityC, cargoBC, UNLOAD),
//                createWaypoint(cityC, cargoAC, UNLOAD)
//        );
//
//        // A -> (10t + 10t) -> B -> (10t + 15t) -> C
//        // Max payload should be 25t (B-C)
//
//        int maxPayload = manager.getMaxPayload(list);
//        assertEquals(maxPayload, 15);
//    }
//
//
//
//    private OrderWaypoint createWaypoint(City city, Cargo cargo, OrderWaypoint.Operation operation) {
//        OrderWaypoint orderWaypoint = new OrderWaypoint();
//        orderWaypoint.setCargo(cargo);
//        orderWaypoint.setCity(city);
//        orderWaypoint.setOperation(operation);
//        return orderWaypoint;
//    }
//
//    private City createCity() {
//        City city = new City();
//        city.setName("Some city");
//        return city;
//    }
//
//    private Cargo createCargo(int weight)
//    {
//        Cargo cargo = new Cargo();
//        cargo.setTitle("Cargo");
//        cargo.setStatus(Cargo.Status.PREPARED);
//        cargo.setWeight(weight);
//        return cargo;
//    }
//
//
//}