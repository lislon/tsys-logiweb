/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto.order;

import com.tsystems.javaschool.logiweb.api.action.dto.CargoWithSrcDestRowDTO;
import lombok.Data;

import java.util.List;

@Data
public class OrderMetaRequestDTO {
    List<CargoWithSrcDestRowDTO> cargoes;
    List<Integer> citiesOrder;
}
