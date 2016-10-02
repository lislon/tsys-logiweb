/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.dao.repos.TruckRepository;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.dto.converter.TruckDTOConverter;
import com.tsystems.javaschool.logiweb.service.exception.DuplicateKeyException;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.tsystems.javaschool.logiweb.service.dto.converter.TruckDTOConverter.copyToDto;
import static com.tsystems.javaschool.logiweb.service.dto.converter.TruckDTOConverter.copyToEntity;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TruckManagerImpl extends BaseManagerImpl<Truck, TruckRepository>
    implements TruckManager {

    private CityManager cityManager;

    @Autowired
    public TruckManagerImpl(TruckRepository truckRepository, CityManager cityManager) {
        super(truckRepository);
        this.cityManager = cityManager;
    }

    /**
     * Returns a list of trucks in specified city, that are ready to accept new order.
     *
     * Returned trucks are not executing any orders and in working condition.
     *
     * @param cityId Location of search.
     * @param maxPayload Weight of cargo to carry.
     * @return List of trucks capable to transport this cargo at specified city.
     */
    public List<TruckDTO> findReadyToGoTrucks(int cityId, int maxPayload)
    {
        List<Truck> readyToGoTrucks = repo.findReadyToGoTrucks(cityId, maxPayload);
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
     * {@inheritDoc}
     */
    @Override
    public void update(int id, TruckDTO dto) throws EntityNotFoundException, DuplicateKeyException {
        checkForDuplicate(dto);

        Truck entity = findOneOrFail(id);

        copyToEntity(cityManager, dto, entity);

        repo.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TruckDTO findDto(int id) throws EntityNotFoundException {
        return copyToDto(findOneOrFail(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer create(TruckDTO dto) throws EntityNotFoundException, DuplicateKeyException {
        checkForDuplicate(dto);

        Truck entity = new Truck();
        copyToEntity(cityManager, dto, entity);

        repo.saveAndFlush(entity);
        return entity.getId();
    }

    private void checkForDuplicate(TruckDTO truck) throws DuplicateKeyException {
        Truck existing = repo.findByName(truck.getName());
        if (existing != null && existing.getId() != truck.getId()) {
            throw new DuplicateKeyException("Truck with number " + truck.getName() + " already exists");
        }
    }

    /**
     * Maps Entities (DAO+service level) to TruckDTO object (service+api layer)
     * @param listOfTrucks List of truck entities
     * @return list of truck TruckDTO
     */
    private List<TruckDTO> getDTOs(Iterable<Truck> listOfTrucks) {
        return StreamSupport.stream(listOfTrucks.spliterator(), false)
                .map(TruckDTOConverter::copyToDto)
                .collect(Collectors.toList());
    }


    
}
