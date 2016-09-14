/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto;

import com.tsystems.javaschool.logiweb.service.dto.OrderCargoDTO;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * Created by Igor Avdeev on 9/14/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BootstrapOrderDTO {
    public Collection<OrderCargoDTO> cargoCollection;
    public Collection<TruckDTO> trucksCollection;
    public Collection<DriverJsonDTO> driversCollection;
    public Integer selectedTruckId;
    public Collection<DriverJsonDTO> selectedDriversCollection;
}
