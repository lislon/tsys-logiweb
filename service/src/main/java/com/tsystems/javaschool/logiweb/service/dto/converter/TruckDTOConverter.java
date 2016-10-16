/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto.converter;

import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;

public class TruckDTOConverter {

    public static TruckDTO copyToDto(Truck from) {

        TruckDTO to = new TruckDTO();

        to.setId(from.getId());
        to.setName(from.getName());
        to.setMaxDrivers(from.getMaxDrivers());
        to.setCapacityKg(from.getCapacityKg());
        to.setCondition(from.getCondition());
        if (from.getCity() != null) {
            to.setCityId(from.getCity().getId());
            to.setCityName(from.getCity().getName());
        }
        return to;
    }

    public static void copyToEntity(CityManager cityManager, TruckDTO from, Truck to) throws EntityNotFoundException {
        to.setId(from.getId());
        to.setName(from.getName());
        to.setMaxDrivers(from.getMaxDrivers());
        to.setCapacityKg(from.getCapacityKg());
        to.setCondition(from.getCondition());
        to.setCity(cityManager.findOneOrFail(from.getCityId()));
    }
}
