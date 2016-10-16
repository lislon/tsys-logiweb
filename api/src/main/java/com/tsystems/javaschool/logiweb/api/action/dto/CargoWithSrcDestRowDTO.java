/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by Igor Avdeev on 9/14/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CargoWithSrcDestRowDTO {
    String name;
    String title;
    int weight;
    int srcCityId;
    int dstCityId;
}
