package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.InvalidStateException;
import com.tsystems.javaschool.logiweb.service.helper.DriverNotifier;
import com.tsystems.javaschool.logiweb.service.helper.RouteCalculator;
import com.tsystems.javaschool.logiweb.service.helper.WorkingHoursCalc;
import com.tsystems.javaschool.logiweb.service.manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderWorkflowManagerImpl implements OrderWorkflowManager {

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private CargoManager cargoManager;

    @Autowired
    private TruckManager truckManager;

    @Autowired
    private DriverManager driverManager;

    @Autowired
    private DriverNotifier driverNotifier;

    @Autowired
    private RouteCalculator routeCalculator;



    /**
     * {@inheritDoc}
     */
    @Override
    public void changeCargoStatus(int cargoId, Cargo.Status cargoStatus) throws EntityNotFoundException, InvalidStateException {
        Cargo cargo = cargoManager.findOneOrFail(cargoId);
        Order order = cargo.getOrder();

        if (cargo.getStatus() == Cargo.Status.SHIPPED) {
            throw new InvalidStateException("Cannot change cargo status. This cargo " + cargo +" is already shipped");
        }


        if (order.isCompleted()) {
            throw new InvalidStateException("Cannot change cargo status, because order is completed");
        }

        cargo.setStatus(cargoStatus);

        if (isAllCargoesShipped(order)) {
            orderManager.markOrderCompleted(order.getId(), true);
        }

        cargoManager.save(cargo);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void assignTruckAndDrivers(int orderId, Integer truckId, List<Integer> drivers) throws EntityNotFoundException, InvalidStateException {

        Order order = orderManager.findOneOrFail(orderId);

        if (truckId != null) {
            Truck truck = truckManager.findOneOrFail(truckId);

            if (truck.getCondition() != Truck.Condition.OK) {
                throw new InvalidStateException("Cannot assign truck " + truck.toString() + " because it's broken");
            }
            order.setTruck(truck);
        } else {
            order.setTruck(null);
        }

        order.setDrivers(Collections.emptySet());

        HashSet<Driver> driverSet = new HashSet<>();

        int routeLength = routeCalculator.getRouteLength(order.getWaypoints());
        double hoursTotalInRoad = routeCalculator.getRouteDuration(routeLength, drivers.size());

        double hoursThisMonth = WorkingHoursCalc.getRequiredWorkHoursInCurrentMonth(
                LocalDateTime.now(),
                LocalDateTime.now().plusHours((long)Math.ceil(hoursTotalInRoad)));

        for (Integer driverId : drivers) {

            Driver driver = driverManager.findOneOrFail(driverId);

            if (driver.getStatus() != Driver.Status.REST) {
                throw new InvalidStateException("Cannot assign driver " + driver.toString() + " because he/she is already on duty");
            }
            int hoursLeftInThisMonth = Driver.MONTH_DUTY_HOURS - driver.getHoursWorked();
            if (hoursLeftInThisMonth < hoursThisMonth) {
                throw new InvalidStateException("Cannot assign driver " + driver.toString()
                        + " because he/she have will overwork otherwise in this month (hoursLeft = " + hoursLeftInThisMonth + ", required hours = " + hoursThisMonth + ")");
            }

            driverSet.add(driver);
        }

        order.setDrivers(driverSet);

        orderManager.save(order);
    }



    private boolean isAllCargoesShipped(Order order) {
        return order.getWaypoints()
                .stream()
                .allMatch(waypoint -> waypoint.getCargo().getStatus() == Cargo.Status.SHIPPED);
    }

}