/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.exception.DuplicateKeyException;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;

import java.util.List;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public interface TruckManager extends BaseManager<Truck> {

    /**
     * Returns a list of trucks in specified city, that are ready to accept new order.
     *
     * Returned trucks are not executing any orders and in working condition.
     *
     * @param cityId Location of search.
     * @param maxPayload Weight of cargo to carry.
     * @return List of trucks capable to transport this cargo at specified city.
     */
    List<TruckDTO> findReadyToGoTrucks(int cityId, int maxPayload);

    /**
     * Returns a list of all trucks, regardless of their condition and location.
     *
     * @return List of all truck.
     */
    List<TruckDTO> findAllTrucks();

    /**
     * Updates truck information in database.
     *
     * @param id Truck id
     * @param dto Truck fields
     * @throws EntityNotFoundException when driver city not found
     * @throws DuplicateKeyException when truck with same name already exists
     */
    void update(int id, TruckDTO dto) throws EntityNotFoundException, DuplicateKeyException;

    /**
     * Returns Driver or throws exception.
     *
     * @param id Truck id
     * @return
     * @throws EntityNotFoundException
     */
    TruckDTO findDto(int id) throws EntityNotFoundException;


    /**
     * Saves the truck and it's location in single transaction
     *
     * @param truck Truck's data
     *
     * @throws EntityNotFoundException when cityId is not found
     * @throws DuplicateKeyException when truck with same name already exists
     */
    Integer create(TruckDTO truck) throws EntityNotFoundException, DuplicateKeyException;
}
