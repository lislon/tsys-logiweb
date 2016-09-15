/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * Created by Igor Avdeev on 9/15/16.
 */
public class OrderWaypointRepository extends BaseRepository<OrderWaypoint> {
    public OrderWaypointRepository(EntityManager em) {
        super(OrderWaypoint.class, em);
    }
}
