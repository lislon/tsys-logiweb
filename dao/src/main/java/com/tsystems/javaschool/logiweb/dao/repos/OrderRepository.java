/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.repos;

import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Igor Avdeev on 9/3/16.
 */
@Service
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("from Order o left join fetch o.truck")
    List<Order> getAllOrdersSummary();

    @Query("from OrderWaypoint ow " +
                "join fetch ow.city " +
                "join fetch ow.cargo " +
                "where ow.order.id = ?1 " +
                "order by ow.waypointWeight asc")
    List<OrderWaypoint> findOrderWaypoints(Integer orderId);

    @Query("from Order o " +
                "join fetch o.drivers d " +
                "join fetch o.truck " +
                "where d.personalCode = ?1")
    Collection<Order> findOrdersForDriver(String personalNumber);
}
