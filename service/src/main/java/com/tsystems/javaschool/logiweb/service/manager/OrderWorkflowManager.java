package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.business.InvalidStateException;

import java.util.List;

public interface OrderWorkflowManager {

    /**
     * Called when driver pick up or delivered cargo
     *
     * @param cargoId Id of cargo
     * @param cargoStatus Cargo status
     */
    void changeCargoStatus(int cargoId, Cargo.Status cargoStatus) throws EntityNotFoundException, InvalidStateException;

    /**
     * Assigns or unassigns truck and drivers for given order
     *
     * @param orderId Id of order
     * @param truckId Assigned truck id
     * @param drivers List of drivers
     * @throws EntityNotFoundException when order, truck or driver is not found.
     * @throws InvalidStateException when truck is broken or driver is assigned to other order.
     */
    void assignTruckAndDrivers(int orderId, Integer truckId, List<Integer> drivers) throws EntityNotFoundException, InvalidStateException;
}
