package com.tsystems.javaschool.logiweb.service.dto.converter;

import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.service.dto.CargoDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;

public class CargoDTOConverter {

    public static CargoDTO convertToDto(Cargo from) {

        CargoDTO to = new CargoDTO();

        to.setId(from.getId());
        to.setName(from.getName());
        to.setTitle(from.getTitle());
        to.setWeight(from.getWeight());
        return to;
    }

    public static void convertToEntity(CargoDTO from, Cargo to) throws EntityNotFoundException {
        to.setName(from.getName());
        to.setTitle(from.getTitle());
        to.setWeight(from.getWeight());
    }
}
