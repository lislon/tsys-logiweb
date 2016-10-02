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
import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Entity
@Table(name = "orders")
@Data
@ToString(exclude = {"waypoints","drivers"})
@NoArgsConstructor
public class Order {

    public enum Status {NEW, PREPARED, FINISHED};

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_created")
    @UpdateTimestamp
    private Timestamp dateCreated;

    @Column(name = "date_completed")
    @Null
    private Date dateCompleted;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    @OrderBy(clause = "waypointWeight asc")
    private SortedSet<OrderWaypoint> waypoints;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @ManyToMany
    @JoinTable(name="orders_drivers", joinColumns=@JoinColumn(name="order_id"),
            inverseJoinColumns=@JoinColumn(name="driver_id"))
    private Set<Driver> drivers;



    /**
     * Returns departure city or null when no waypoints have been set.
     *
     * @return Departure city
     */
    public City getDepartureCity() {
        if (waypoints.size() > 0) {
            return waypoints.first().getCity();
        }
        return null;
    }



}
