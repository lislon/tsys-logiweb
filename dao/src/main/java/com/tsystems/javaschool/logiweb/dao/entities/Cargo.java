/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Set;

/**
 * Cargo for transportation (Contains in OrderWaypoint)
 */
@Entity
@Table(name = "cargoes")
public class Cargo {
    public enum Status { PREPARED, SHIPPED, DELIVERED }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Unique cargo identifier for given order.
     */
    @Column(name = "name")
    @NotNull
    @Size(min = 1)
    private String name;

    /**
     * Cargo description.
     */
    @Column(name = "title")
    @NotNull
    @Size(min = 1)
    private String title;

    /**
     * Cargo weight in kg.
     */
    @Column(name = "weight")
    @Min(1)
    private Integer weight;

    /**
     * Owning order.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Order waypoints where operations are carried with this cargo.
     */
    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL)
    private Set<OrderWaypoint> waypoints;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cargo))
            return false;
        if (obj == this)
            return true;

        Cargo rhs = (Cargo) obj;
        return (new EqualsBuilder())
                .append(name, rhs.name)
                .build();
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", weight=" + weight +
                ", status=" + status +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Set<OrderWaypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(Set<OrderWaypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
