/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.service.dto.CargoDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;

import java.util.Collection;

public interface CargoManager extends BaseManager<Cargo> {
    /**
     * Saves the cargo to database and assigns it to given order
     * @param cargoDTO Cargo data
     * @param orderId Order which cargo belongs to
     * @return created cargo id
     * @throws EntityNotFoundException when order is not found
     */
    int createCargo(CargoDTO cargoDTO, int orderId) throws EntityNotFoundException;

    /**
     * Updates cargo information
     * @param cargoId Cargo identifies
     * @param cargoDTO
     * @throws EntityNotFoundException when cargo to be edited is not found
     */
    void updateCargo(int cargoId, CargoDTO cargoDTO) throws EntityNotFoundException;

    Collection<CargoDTO> getOrderCargoes(int orderId);
}
