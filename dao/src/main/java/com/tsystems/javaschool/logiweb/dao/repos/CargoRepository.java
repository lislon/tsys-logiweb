/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.Cargo;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;

import javax.persistence.EntityManager;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
public class CargoRepository extends BaseRepository<Cargo> {
    public CargoRepository(EntityManager em) {
        super(Cargo.class, em);
    }
}
