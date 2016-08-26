/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;

import javax.persistence.EntityManager;


/**
 * Created by Igor Avdeev on 8/23/16.
 */
public class DriverRepository extends BaseRepository<Driver> {

    public DriverRepository(EntityManager manager) {
        super(Driver.class, manager);
    }
}
