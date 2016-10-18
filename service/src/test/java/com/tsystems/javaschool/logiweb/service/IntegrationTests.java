/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.service.dto.CargoDTO;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.dto.WaypointDTO;
import com.tsystems.javaschool.logiweb.service.manager.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.*;
import static com.tsystems.javaschool.logiweb.service.IntegrationTestConstants.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = { "classpath*:/META-INF/spring/applicationContext.xml" }),
        @ContextConfiguration("/testApplicationContext.xml")
})
@Sql
@Transactional
public class IntegrationTests {

    @Autowired
    OrderManager orderManager;

    @Autowired
    CargoManager cargoManager;

    @Autowired
    TruckManager truckManager;

    @Autowired
    DriverManager driverManager;

    @Autowired
    OrderWorkflowManager orderWorkflowManager;

    private TruckDTO moscowTruckDTO;
    private List<WaypointDTO> waypointDTOList;

    @Before
    public void setUp() throws Exception {
        moscowTruckDTO = new TruckDTO();
        moscowTruckDTO.setName("FH00001");
        moscowTruckDTO.setMaxDrivers(1);
        moscowTruckDTO.setCapacityKg(10000);
        moscowTruckDTO.setCityId(CITY_ID_MOSCOW);
        moscowTruckDTO.setCondition(Truck.Condition.OK);
        waypointDTOList = new LinkedList<>();
    }

    /**
     * Tests that order can be created
     * @throws Exception
     */
    @Test
    public void canCreateOrder() throws Exception {
        Integer orderId = orderManager.createOrder();

        Integer cargoId1 = cargoManager.createCargo(new CargoDTO("moscow->spb", "title", 100), orderId);
        Integer cargoId2 = cargoManager.createCargo(new CargoDTO("moscow->berlin", "title", 100), orderId);

        List<WaypointDTO> waypointDTOList = new LinkedList<>();
        waypointDTOList.add(new WaypointDTO(cargoId1, CITY_ID_MOSCOW, OrderWaypoint.Operation.LOAD));
        waypointDTOList.add(new WaypointDTO(cargoId1, CITY_ID_SPB, OrderWaypoint.Operation.UNLOAD));
        waypointDTOList.add(new WaypointDTO(cargoId2, CITY_ID_MOSCOW, OrderWaypoint.Operation.LOAD));
        waypointDTOList.add(new WaypointDTO(cargoId2, CITY_ID_BERLIN, OrderWaypoint.Operation.UNLOAD));


        orderManager.updateWaypoints(orderId, waypointDTOList);

        Order order = orderManager.findOneOrFail(orderId);

        assertEquals(CITY_ID_MOSCOW, order.getWaypoints().first().getCity().getId());
        assertEquals(CITY_ID_BERLIN, order.getWaypoints().last().getCity().getId());
        assertEquals(cargoId1, order.getWaypoints().first().getCargo().getId());
    }

    @Test
    public void canAssignTruckAndDrivers() throws Exception {
        Integer orderId = orderManager.createOrder();
        Integer truckId = truckManager.create(moscowTruckDTO);

        DriverDTO driver1 = new DriverDTO(null, "test", "test", "test1", Driver.Status.REST, 50, CITY_ID_MOSCOW, "Moscow", null);
        DriverDTO driver2 = new DriverDTO(null, "test", "test", "test2", Driver.Status.REST, 50, CITY_ID_MOSCOW, "Moscow", null);

        Integer driverId1 = driverManager.create(driver1);
        Integer driverId2 = driverManager.create(driver2);

        Integer cargoId2 = cargoManager.createCargo(new CargoDTO("moscow->berlin", "title", 100), orderId);

        List<WaypointDTO> waypointDTOList = new LinkedList<>();
        waypointDTOList.add(new WaypointDTO(cargoId2, CITY_ID_MOSCOW, OrderWaypoint.Operation.LOAD));
        waypointDTOList.add(new WaypointDTO(cargoId2, CITY_ID_BERLIN, OrderWaypoint.Operation.UNLOAD));

        orderManager.updateWaypoints(orderId, waypointDTOList);

        orderWorkflowManager.assignTruckAndDrivers(orderId, truckId, Arrays.asList(driverId1, driverId2));

        Order order = orderManager.findOneOrFail(orderId);

        assertEquals(truckId, order.getTruck().getId());
        assertEquals(2, order.getDrivers().size());
    }

//    @Test
//    public void throwsExceptionOnWrongCityOrder() throws Exception {
//        int orderId = orderManager.createOrder();
//
//        int cargoId1 = cargoManager.createCargo(new CargoDTO("moscow->spb", "title", 100), orderId);
//        int cargoId2 = cargoManager.createCargo(new CargoDTO("moscow->berlin", "title", 100), orderId);
//
//        List<CargoWaypointDTO> waypointDTOList = new LinkedList<>();
//        waypointDTOList.add(new CargoWaypointDTO(cargoId1, CITY_ID_MOSCOW, OrderWaypoint.Operation.LOAD));
//        waypointDTOList.add(new CargoWaypointDTO(cargoId1, CITY_ID_SPB, OrderWaypoint.Operation.UNLOAD));
//        waypointDTOList.add(new CargoWaypointDTO(cargoId2, CITY_ID_SPB, OrderWaypoint.Operation.LOAD));
//        waypointDTOList.add(new CargoWaypointDTO(cargoId2, CITY_ID_MOSCOW, OrderWaypoint.Operation.UNLOAD));
//        orderManager.updateWaypoints(orderId, waypointDTOList);
//    }
}
