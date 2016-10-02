/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Igor Avdeev on 9/13/16.
 */
@Data
public class CargoLegDTO {
    private String name;
    private String title;
    private int ordinal;
    private int weight;

    private int srcCityId;
    private String srcCityName;

    private int dstCityId;
    private String dstCityName;

}
