/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.dao.repos.TruckRepository;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.exception.DuplicateKeyException;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public class TruckManagerImpl extends BaseManagerImpl<Truck, TruckRepository>
    implements TruckManager {

    public TruckManagerImpl(TruckRepository repo, ServiceContainer services) {
        super(repo, services);
    }

    /**
     * Returns a list of trucks in specified city, that are ready to accept new order.
     *
     * Returned trucks are not executing any orders and in working condition.
     *
     * @param city Location of search.
     * @param maxPayload Weight of cargo to carry.
     * @return List of trucks capable to transport this cargo at specified city.
     */
    public List<TruckDTO> findReadyToGoTrucks(City city, int maxPayload)
    {
        List<Truck> readyToGoTrucks = repo.findReadyToGoTrucks(city, maxPayload);
        return getDTOs(readyToGoTrucks);
    }

    /**
     * Returns a list of all trucks, regardless of their condition and location.
     *
     * @return List of all truck.
     */
    public List<TruckDTO> findAllTrucks()
    {
        // TODO: Optimization. We also need cities name for this query
        return getDTOs(repo.findAll());
    }

    /**
     * Saves the truck and it's location in single transaction
     *
     * @param truck
     * @param cityId
     * @throws EntityNotFoundException when cityId is not found
     * @throws DuplicateKeyException when truck with same name already exists
     */
    public void save(Truck truck, int cityId) throws EntityNotFoundException, DuplicateKeyException
    {
        Truck existing = repo.findByName(truck.getName());
        if (existing != null && existing.getId() != truck.getId()) {
            throw new DuplicateKeyException("Truck with number " + truck.getName() + " already exists");
        }
        City city = services.getCityManager().findOne(cityId);
        truck.setCity(city);
        if (truck.getId() > 0) {
            repo.update(truck);
        } else {
            repo.create(truck);
        }
    }


    /**
     * Maps Entities (DAO+service level) to TruckDTO object (service+api layer)
     * @param listOfTrucks List of truck entities
     * @return list of truck TruckDTO
     */
    private List<TruckDTO> getDTOs(List<Truck> listOfTrucks) {
        return listOfTrucks
                .stream()
                .map(TruckDTO::fromEntity)
                .collect(Collectors.toList());
    }


}
