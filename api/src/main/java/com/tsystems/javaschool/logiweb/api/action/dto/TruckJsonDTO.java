/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Created by Igor Avdeev on 9/14/16.
 */
@AllArgsConstructor
@NoArgsConstructor
public class TruckJsonDTO {
    public int id;
    public String name;
    public int maxDrivers;
    public int capacityTon;
//
//    public static TruckJsonDTO map(Truck truck) {
//        TruckJsonDTO that = new TruckJsonDTO();
//        that.id = truck.getId();
//        that.name = truck.getName();
//        that.maxDrivers = truck.getMaxDrivers();
//        that.capacityTon = (int)Math.floor(truck.getCapacityKg() / 1000);
//
//        return that;
//    }
}
