/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Igor Avdeev on 9/14/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CargoJsonDTO {
    public String name;
    public String title;
    public int weight;
    public int srcCityId;
    public int dstCityId;
}
