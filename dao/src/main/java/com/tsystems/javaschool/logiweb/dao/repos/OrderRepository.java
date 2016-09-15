/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Igor Avdeev on 9/3/16.
 */
public class OrderRepository extends BaseRepository<Order> {
    public OrderRepository(EntityManager em) {
        super(Order.class, em);
    }

    public List<Order> getAllOrdersSummary()
    {
        Query query = em.createQuery("from Order o left join fetch o.truck");
        List<Order> resultList = (List<Order>)query.getResultList();

        return resultList;
    }

    public List<OrderWaypoint> findOrderWaypoints(int orderId) {
        Query query = em.createQuery("from OrderWaypoint ow " +
                "join fetch ow.city " +
                "join fetch ow.cargo " +
                "where ow.order.id = :id " +
                "order by ow.waypointWeight asc");
        query.setParameter("id", orderId);
        return (List<OrderWaypoint>)query.getResultList();
    }

    public Collection<Order> findOrdersForDriver(String personalNumber) {
        Query query = em.createQuery("from Order o " +
                "join fetch o.drivers d " +
                "join fetch o.truck " +
                "where d.personalCode = :personalNumber");
        query.setParameter("personalNumber", personalNumber);
        return (List<Order>)query.getResultList();
    }
}
