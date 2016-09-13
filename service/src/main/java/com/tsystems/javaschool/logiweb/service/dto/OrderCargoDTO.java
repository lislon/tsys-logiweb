/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.dao.entities.City;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Igor Avdeev on 9/13/16.
 */
@Data
@Builder
public class OrderCargoDTO {
    private String name;
    private String title;
    private int ordinal;
    private int weight;

    private int srcCityId;
    private String srcCityName;

    private int dstCityId;
    private String dstCityName;

}
