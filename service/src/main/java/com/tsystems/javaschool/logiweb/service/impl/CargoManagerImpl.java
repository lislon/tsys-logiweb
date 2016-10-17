/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.impl;

import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.repos.CargoRepository;
import com.tsystems.javaschool.logiweb.dao.repos.OrderRepository;
import com.tsystems.javaschool.logiweb.service.dto.CargoDTO;
import com.tsystems.javaschool.logiweb.service.dto.converter.CargoDTOConverter;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.CargoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CargoManagerImpl extends BaseManagerImpl<Cargo, CargoRepository> implements CargoManager {

    private OrderRepository orderRepository;

    @Autowired
    public CargoManagerImpl(OrderRepository orderRepository, CargoRepository repo) {
        super(repo);
        this.orderRepository = orderRepository;
    }

    /**
     * {@inheritDoc}
     */
    public int createCargo(CargoDTO cargoDTO, int orderId) throws EntityNotFoundException {
        Order order = orderRepository.findOne(orderId);

        // validate order existence
        if (order == null) {
            throw new EntityNotFoundException("Order", orderId);
        }
        Cargo cargo = new Cargo();
        cargo.setOrder(order);
        CargoDTOConverter.convertToEntity(cargoDTO, cargo);

        repo.saveAndFlush(cargo);
        return cargo.getId();
    }

    /**
     * {@inheritDoc}
     */
    public void updateCargo(int cargoId, CargoDTO cargoDTO) throws EntityNotFoundException {
        Cargo cargo = findOneOrFail(cargoId);
        CargoDTOConverter.convertToEntity(cargoDTO, cargo);

        repo.save(cargo);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<CargoDTO> getOrderCargoes(int orderId) {
        List<Cargo> cargoList = repo.findByOrderId(orderId);
        return cargoList.stream()
                .map(CargoDTOConverter::convertToDto)
                .collect(Collectors.toList());
    }

}
