/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Igor Avdeev on 9/3/16.
 */
@Entity
@Table(name = "orders_waypoints")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"order"})
@NamedQuery(name = "orderWaypoint.findByOrderId",
        query = "from OrderWaypoint w where w.order.id = :orderId order by w.waypointWeight asc")
public class OrderWaypoint implements Comparable<OrderWaypoint> {

    public enum Operation { LOAD, UNLOAD }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;


    @Column(name = "is_completed")
    private boolean isCompleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation")
    private Operation operation;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "waypoint_weight")
    private int waypointWeight;

    @Override
    public int compareTo(OrderWaypoint o) {
        if (o.waypointWeight == waypointWeight) {
            return 0;
        }
        if (o.waypointWeight > waypointWeight) {
            return 1;
        }
        return -1;
    }
}
