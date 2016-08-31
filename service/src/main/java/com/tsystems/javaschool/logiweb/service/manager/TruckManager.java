/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.dao.repos.TruckRepository;

import java.util.List;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public class TruckManager {

    private TruckRepository repo = null;

    public TruckManager(TruckRepository repo) {
        this.repo = repo;
    }

    /**
     * Returns a list of trucks in specified city, that are ready to accept new order.
     *
     * Returned trucks are not executing any orders and in working condition.
     *
     * @param city Location of search.
     * @param cargoWeight Weight of cargo to carry.
     * @return List of trucks capable to transport this cargo at specified city.
     */
    public List<Truck> findReadyToGoTrucks(City city, int cargoWeight)
    {
        return repo.findReadyToGoTrucks(city, cargoWeight);
    }

    /**
     * Returns a list of all trucks, regardless of their condition and location.
     *
     * @return List of all truck.
     */
    public List<Truck> findAllTrucks()
    {
        // TODO: For optimization we also need cities name for this query
        return (List<Truck>)repo.findAll();
    }

    public Truck find(int id)
    {
        return repo.find((Object)id);
    }

    public void save(Truck truck) {
        // TODO: implement
    }
}
