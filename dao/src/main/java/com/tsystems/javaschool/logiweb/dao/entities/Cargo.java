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
@ToString(exclude = {"order", "waypoints"})
@Data
@NoArgsConstructor
public class Cargo {
    public enum Status { PREPARED, SHIPPED, DELIVERED }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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

}
