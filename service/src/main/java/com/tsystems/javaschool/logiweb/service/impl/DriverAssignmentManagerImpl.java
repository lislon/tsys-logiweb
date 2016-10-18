package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.service.dto.CargoWaypointDTO;
import com.tsystems.javaschool.logiweb.service.dto.DriverAssignmentDTO;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.helper.RouteCalculator;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DriverAssignmentManagerImpl {

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private DriverManager driverManager;

    @Autowired
    private RouteCalculator routeCalculator;

    /**
     * Returns current driver order assignment with route points, distance and other relevant information.
     * @param driverId driver id
     * @return driver assignment or null if driver have none of them.
     * @throws EntityNotFoundException when driver id not found
     */
    public DriverAssignmentDTO findCurrentDriverAssignment(int driverId) throws EntityNotFoundException {
        Order o = driverManager.findOrderByDriverId(driverId);
        if (o == null) {
            return null;
        }

        DriverAssignmentDTO dto = new DriverAssignmentDTO();
        dto.setOrderId(o.getId());
        dto.setCoDrivers(o.getDrivers().stream()
                .map(d -> {
                    DriverDTO driverDTO = new DriverDTO();
                    driverDTO.setFirstName(d.getFirstName());
                    driverDTO.setLastName(d.getLastName());
                    driverDTO.setPersonalCode(d.getPersonalCode());
                    return driverDTO;
                })
                .collect(Collectors.toList()));

        dto.setCityXOperationXWaypoints(getWaypointsGrouped(o));
        dto.setTruckLicencePlate(o.getTruck().getName());
        dto.setSrcCityName(o.getWaypoints().first().getCity().getName());
        dto.setDstCityName(o.getWaypoints().last().getCity().getName());
        dto.setRouteDistance(routeCalculator.getRouteDistance(o.getWaypoints()));
        dto.setRouteDuration(routeCalculator.getRouteDuration(dto.getRouteDistance(), dto.getCoDrivers().size()));

        TruckDTO truckDTO = new TruckDTO();
        truckDTO.setName(o.getTruck().getName());
        truckDTO.setId(o.getTruck().getId());
        truckDTO.setCapacityKg(o.getTruck().getCapacityKg());
        truckDTO.setMaxDrivers(o.getTruck().getMaxDrivers());

        dto.setTruck(truckDTO);


        return dto;
    }

    private Map<String, Map<OrderWaypoint.Operation, List<CargoWaypointDTO>>> getWaypointsGrouped(Order o) {
        Map<String, List<CargoWaypointDTO>> waypointsByCity = o.getWaypoints()
                .stream()
                .map(w -> new CargoWaypointDTO(
                        w.getCity().getName(),
                        w.getOperation(),
                        w.getCargo().getName(),
                        w.getCargo().getTitle()
                ))
                .collect(Collectors.groupingBy(
                        CargoWaypointDTO::getCityName,
                        LinkedHashMap::new,
                        Collectors.toList()));


        Map<String, Map<OrderWaypoint.Operation, List<CargoWaypointDTO>>> waypointsByCityByOperation = new LinkedHashMap<>();

        for (Map.Entry<String, List<CargoWaypointDTO>> entry : waypointsByCity.entrySet()) {
            waypointsByCityByOperation.put(entry.getKey(), entry.getValue()
                    .stream()
                    .collect(Collectors.groupingBy(CargoWaypointDTO::getOperation)));
        }
        return waypointsByCityByOperation;
    }

}
