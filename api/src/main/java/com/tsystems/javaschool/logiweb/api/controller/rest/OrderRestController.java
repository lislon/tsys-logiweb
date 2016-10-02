/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller.rest;

import com.tsystems.javaschool.logiweb.api.action.dto.RouteMetaResponseDTO;
import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.service.dto.CargoLegDTO;
import com.tsystems.javaschool.logiweb.service.dto.FullOrderDataDTO;
import com.tsystems.javaschool.logiweb.service.dto.OrderSummaryDTO;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.dto.converter.CargoLegDTOConverter;
import com.tsystems.javaschool.logiweb.service.dto.converter.TruckDTOConverter;
import com.tsystems.javaschool.logiweb.service.exception.BusinessLogicException;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.helper.OrderCreatorService;
import com.tsystems.javaschool.logiweb.service.helper.RouteCalculator;
import com.tsystems.javaschool.logiweb.service.manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

//import com.tsystems.javaschool.logiweb.service.dto.OrderDTO;

/**
 * Created by Igor Avdeev on 10/4/16.
 */
@RestController
@RequestMapping("/orders/api")
public class OrderRestController extends BaseRestController {

    @Autowired
    private OrderManager manager;

    @Autowired
    private TruckManager truckManager;

    @Autowired
    private DriverManager driverManager;

    @Autowired
    private CargoManager cargoManager;

    @Autowired
    private OrderCreatorService orderCreatorService;

    @Autowired
    private CityManager cityManager;

    @Autowired
    private RouteCalculator routeCalculator;

    @Autowired
    private OrderWorkflowManager orderWorkflowManager;

    /**
     * Returns route length, duration and list of avaliavble trucks.
     * @param route   Information about cargoes and operations
     * @param orderId Existing orderId for not excluding selected truck as assigned
     * @return
     * @throws EntityNotFoundException when city or order is not found by id.
     */
    @PostMapping(value = "/routeMeta", consumes = "application/json")
    public @ResponseBody RouteMetaResponseDTO routeMeta(@RequestBody FullOrderDataDTO route,
                                                        @RequestParam(name = "orderId", required = false) Integer orderId)
            throws EntityNotFoundException {

//        List<City> cities = route.getCargoes().stream()
//                .map(cityManager::find)
//                .collect(Collectors.toList());

        List<Cargo> cargos = route.getCargoes().stream()
                .map(dto -> {
                    Cargo c = new Cargo();
                    c.setName(dto.getName());
                    c.setTitle(dto.getTitle());
                    c.setWeight(dto.getWeight());
                    return c;
                })
                .collect(Collectors.toList());


        List<OrderWaypoint> waypoints = new LinkedList<>();

        for (FullOrderDataDTO.WaypointNoDTO dto : route.getWaypoints()) {
            OrderWaypoint ow = new OrderWaypoint();
            ow.setCity(cityManager.findOneOrFail(dto.getCityId()));
            ow.setCargo(cargos.get(dto.getCargoNo()));
            ow.setOperation(dto.getOperation());
            waypoints.add(ow);
        }

        int maxPayload = routeCalculator.getMaxPayload(waypoints);
        int routeLength = routeCalculator.getRouteLength(waypoints);

        List<TruckDTO> truckList = new LinkedList<>();

        if (waypoints.size() > 0) {

            List<TruckDTO> readyToGoTrucks = truckManager.findReadyToGoTrucks(waypoints.get(0).getCity().getId(), maxPayload);

            // add selected truckId if any
            if (orderId != null) {
                TruckDTO truckDTO = TruckDTOConverter.copyToDto(manager.findOneOrFail(orderId).getTruck());
                readyToGoTrucks.add(truckDTO);
            }
        }


        return new RouteMetaResponseDTO(routeLength, maxPayload, truckList);
    }

    @GetMapping("")
    public @ResponseBody List<OrderSummaryDTO> apiList() {
        return manager.getAllOrdersSummary();
    }

    @PostMapping("/new")
    public void createApi(@RequestBody FullOrderDataDTO route) throws BusinessLogicException {
        orderCreatorService.createOrder(route);
    }

    @PostMapping("/{id}")
    public @ResponseBody void update(@PathVariable("id") int id, @RequestBody FullOrderDataDTO fullOrderDataDTO) throws BusinessLogicException {
        orderWorkflowManager.assignTruckAndDrivers(id, fullOrderDataDTO.getSelectedTruckId(), fullOrderDataDTO.getSelectedDrivers());
    }


    @DeleteMapping("/{id}")
    public void apiDelete(@PathVariable("id") int id) throws EntityNotFoundException {
        manager.delete(id);
    }

    @DeleteMapping("/{id}/cargoes")
    public Collection<CargoLegDTO> listOrderCargoes(@PathVariable("id") int id) throws EntityNotFoundException {
        return CargoLegDTOConverter.toCargoLegs(manager.findOneOrFail(id));
    }
}
