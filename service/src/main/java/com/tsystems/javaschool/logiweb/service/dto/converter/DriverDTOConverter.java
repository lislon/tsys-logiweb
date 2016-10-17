/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto.converter;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.UserManager;

public class DriverDTOConverter {

    public static DriverDTO convertToDto(Driver from) throws EntityNotFoundException {

        if (from == null) {
            return null;
        }

        DriverDTO to = new DriverDTO();

        to.setId(from.getId());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setStatus(from.getStatus());
        to.setHoursWorked(from.getHoursWorked());
        to.setPersonalCode(from.getPersonalCode());
        if (from.getCity() != null) {
            to.setCityId(from.getCity().getId());
            to.setCityName(from.getCity().getName());
        }
        if (from.getUser() != null) {
            to.setUserId(from.getUser().getId());
        }
        return to;
    }

    public static void convertToEntity(CityManager cityManager, UserManager userManager, DriverDTO from, Driver to) throws EntityNotFoundException {

        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setStatus(from.getStatus());
        to.setHoursWorked(from.getHoursWorked());
        to.setPersonalCode(from.getPersonalCode());

        if (from.getCityId() != null) {
            to.setCity(cityManager.findOneOrFail(from.getCityId()));
        }
        if (from.getUserId() != null) {
            to.setUser(userManager.findOneOrFail(from.getUserId()));
        }
    }
}
