/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto;

import java.util.List;

/**
 * DTO for requesting distance and trucks
 */
public class OrderDataDTO {
    public List<CargoJsonDTO> cargoes;
    public List<Integer> citiesOrder;
    public Integer selectedTruckId;
    public List<Integer> selectedDrivers;
}
