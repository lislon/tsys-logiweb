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
public class Order {

    public enum Status {NEW, PREPARED, FINISHED}

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

    @OneToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @ManyToMany
    @JoinTable(name="orders_drivers", joinColumns=@JoinColumn(name="order_id"),
            inverseJoinColumns=@JoinColumn(name="driver_id"))
    private Set<Driver> drivers;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", isCompleted=" + isCompleted +
                ", waypoints=" + waypoints +
                ", truck=" + truck +
                ", drivers=" + drivers +
                '}';
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public SortedSet<OrderWaypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(SortedSet<OrderWaypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }
}
