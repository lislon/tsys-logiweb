/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto;

import com.tsystems.javaschool.logiweb.api.action.dto.driver.DriverJsonDTO;
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
    public Collection<TruckDTO> truckCollection;
    public Collection<DriverJsonDTO> driverCollection;
    public Integer selectedTruckId;
    public Collection<DriverJsonDTO> selectedDriverCollection;
    public Integer routeLength;
    public Integer requiredCapacity;
}
