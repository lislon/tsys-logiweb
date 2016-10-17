package com.tsystems.javaschool.logiweb.service.facade;

import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.service.dto.DriverAssignmentDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.helper.RouteCalculator;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DriverAssignmentFacade {

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private RouteCalculator routeCalculator;

    /**
     * Returns current driver order assignment with route points, distance and other relevant information.
     * @param driverId
     * @return driver assignment or null if driver have none of them.
     * @throws EntityNotFoundException when driver id not found
     */
    public DriverAssignmentDTO findCurrentDriverAssignment(int driverId) throws EntityNotFoundException {
        Order o = orderManager.findOrderByDriver(driverId);
        if (o == null) {
            return null;
        }

        return new DriverAssignmentDTO(
                o.getId(),
                o.getDrivers().stream()
                        .map(d -> d.getPersonalCode() + " (" + d.getFirstName() + ")")
                        .collect(Collectors.toList()),
                o.getWaypoints().stream()
                        .map(w -> new DriverAssignmentDTO.WaypointDTO(
                                w.getCity().getName(),
                                w.getOperation(),
                                w.getCargo().getName()
                        )).collect(Collectors.toList()),
                o.getTruck().getName(),
                o.getWaypoints().first().getCity().getName() + " -> " + o.getWaypoints().last().getCity().getName(),
                o.isCompleted() ? "Completed" : "Not completed"
        );

    }

}
