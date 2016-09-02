/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.dao.repos.TruckRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public class TruckManager {

    @Data
    @AllArgsConstructor
    public static class DTO
    {
        public final int id;
        public final String name;
        public final int maxDrivers;
        public final int capacityKg;
        public final Truck.Condition condition;
        public final int cityId;
    }

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
    public List<DTO> findReadyToGoTrucks(City city, int cargoWeight)
    {
        List<Truck> readyToGoTrucks = repo.findReadyToGoTrucks(city, cargoWeight);
        return getDTOs(readyToGoTrucks);
    }

    /**
     * Returns a list of all trucks, regardless of their condition and location.
     *
     * @return List of all truck.
     */
    public List<DTO> findAllTrucks()
    {
        // TODO: For optimization we also need cities name for this query
        return getDTOs(repo.findAll());
    }

    public DTO find(int id)
    {
        return Entity2DTO(repo.find((Object)id));
    }

    public boolean delete(int id) {
        return repo.delete(id);
    }

    public void save(DTO truck) {

        City city = new City();
        city.setId(truck.getCityId());

        Truck entity;

        if (truck.getId() > 0) {
            entity = repo.find(truck.getId());
        } else {
            entity = new Truck();
        }

        entity.setName(truck.getName());
        entity.setCapacityKg(truck.getCapacityKg());
        entity.setCondition(truck.getCondition());
        entity.setMaxDrivers(truck.getMaxDrivers());
        entity.setCity(city);

        repo.save(entity);
    }


    /**
     * Maps Entities (DAO+service level) to DTO object (service+api layer)
     * @param listOfTrucks List of truck entities
     * @return list of truck DTO
     */
    private List<DTO> getDTOs(List<Truck> listOfTrucks) {
        return listOfTrucks
                .stream()
                .map(TruckManager::Entity2DTO)
                .collect(Collectors.toList());
    }

    private static DTO Entity2DTO(Truck t) {
        if (t == null) {
            return null;
        }
        return new DTO(
                t.getId(),
                t.getName(),
                t.getMaxDrivers(),
                t.getCapacityKg(),
                t.getCondition(),
                t.getCity().getId()
        );
    }
}
