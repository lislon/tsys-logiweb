/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto;

import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Created by Igor Avdeev on 9/14/16.
 */
@AllArgsConstructor
public class RouteMetaResponseDTO {
    public int length;
    public int requiredCapacity;
    public List<TruckDTO> trucks;
}
