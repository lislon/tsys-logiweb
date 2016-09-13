/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.dao.repos.CityRepository;
import com.tsystems.javaschool.logiweb.dao.repos.DriverRepository;
import com.tsystems.javaschool.logiweb.dao.repos.OrderRepository;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.impl.CityManagerImpl;
import com.tsystems.javaschool.logiweb.service.impl.DriverManagerImpl;
import com.tsystems.javaschool.logiweb.service.impl.OrderManagerImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint.Operation.*;

/**
 * Created by Igor Avdeev on 9/5/16.
 */
public class OrderManagerTest {

    OrderRepository mockRepo;
    OrderManagerImpl manager;
    ServiceContainer container;

    @Before
    public void createManager() {
        mockRepo = mock(OrderRepository.class);
        container = mock(ServiceContainer.class);
        manager = new OrderManagerImpl(mockRepo, container);

    }

    @Test
    public void getRouteLength() throws Exception {

        when(container.getCityManager()).
                thenReturn(new CityManagerImpl(mock(CityRepository.class), container));

        City moscow = new City();
        moscow.setName("Moscow");
        moscow.setLat(55.75222);
        moscow.setLng(37.61556);

        City spb = new City();
        spb.setName("Spb");
        spb.setLat(59.93863);
        spb.setLng(30.31413);

        City munich = new City();
        munich.setName("Munich");
        munich.setLat(48.13743);
        munich.setLng(11.57549);

        Cargo cargoAB = createCargo(10);
        Cargo cargoBC = createCargo(15);

        List<OrderWaypoint> route = Arrays.asList(
                createWaypoint(moscow, cargoAB, LOAD),
                createWaypoint(spb, cargoAB, UNLOAD),
                createWaypoint(spb, cargoBC, LOAD),
                createWaypoint(munich, cargoBC, UNLOAD)
        );

        int routeLength = manager.getRouteLength(route);
        assertEquals(2415, routeLength);
    }

    @Test
    public void getMaxPayload() throws Exception {

        Cargo cargoAB = createCargo(10);
        Cargo cargoAC = createCargo(10);
        Cargo cargoBC = createCargo(15);

        City cityA = createCity();
        City cityB = createCity();
        City cityC = createCity();

        List<OrderWaypoint> list = Arrays.asList(
                createWaypoint(cityA, cargoAB, LOAD),
                createWaypoint(cityA, cargoAC, LOAD),
                createWaypoint(cityB, cargoAB, UNLOAD),
                createWaypoint(cityB, cargoBC, LOAD),
                createWaypoint(cityC, cargoBC, UNLOAD),
                createWaypoint(cityC, cargoAC, UNLOAD)
        );

        // A -> (10t + 10t) -> B -> (10t + 15t) -> C
        // Max payload should be 25t (B-C)

        int maxPayload = manager.getMaxPayload(list);
        assertEquals(maxPayload, 15);
    }

    private OrderWaypoint createWaypoint(City city, Cargo cargo, OrderWaypoint.Operation operation)
    {
        OrderWaypoint orderWaypoint = new OrderWaypoint();
        orderWaypoint.setCargo(cargo);
        orderWaypoint.setCity(city);
        orderWaypoint.setOperation(operation);
        return orderWaypoint;
    }

    private City createCity()
    {
        City city = new City();
        city.setName("City");
        return city;
    }

    private Cargo createCargo(int weight)
    {
        Cargo cargo = new Cargo();
        cargo.setTitle("Cargo");
        cargo.setStatus(Cargo.Status.PREPARED);
        cargo.setWeight(weight);
        return cargo;
    }

}