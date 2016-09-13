/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.*;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Null;
import java.sql.Timestamp;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Entity
@Table(name = "orders")
@Data
@ToString(exclude = "waypoints")
@NoArgsConstructor
public class Order {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "date_created")
    @UpdateTimestamp
    private Timestamp dateCreated;

    @Column(name = "date_completed")
    @Null
    private Timestamp dateCompleted;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @OrderBy(clause = "waypointWeight asc")
    private SortedSet<OrderWaypoint> waypoints;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;

}
