/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Igor Avdeev on 9/3/16.
 */
@Entity
@Table(name = "orders_waypoints")
public class OrderWaypoint implements Comparable<OrderWaypoint> {

    public enum Operation { LOAD, UNLOAD }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Column(name = "is_completed")
    private boolean isCompleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation")
    private Operation operation;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "waypoint_weight")
    private int waypointWeight;

    @Override
    public int compareTo(OrderWaypoint rhs) {
        Integer build = (new CompareToBuilder())
                .append(waypointWeight, rhs.waypointWeight)
                .append(operation, rhs.operation)
                .append(city.getId(), rhs.city.getId())
                .append(cargo.getId(), rhs.cargo.getId())
                .build();
        return build;
    }


    @Override
    public int hashCode() {
        Integer build = (new HashCodeBuilder())
                .append(operation)
                .append(city.getId())
                .append(cargo.getId())
                .build();
        return build;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrderWaypoint))
            return false;
        if (obj == this)
            return true;

        OrderWaypoint rhs = (OrderWaypoint) obj;
        return (new EqualsBuilder())
                .append(operation, rhs.operation)
                .append(city.getId(), rhs.city.getId())
                .append(cargo.getId(), rhs.cargo.getId())
                .build();
    }

    @Override
    public String toString() {
        return "OrderWaypoint{" +
                "id=" + id +
                ", isCompleted=" + isCompleted +
                ", operation=" + operation +
                ", city=" + city +
                ", cargo=" + cargo +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getWaypointWeight() {
        return waypointWeight;
    }

    public void setWaypointWeight(int waypointWeight) {
        this.waypointWeight = waypointWeight;
    }
}
