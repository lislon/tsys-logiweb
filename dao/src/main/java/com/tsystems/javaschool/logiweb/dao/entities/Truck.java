/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Set;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Entity
@Table(name = "trucks")
@ToString(exclude = "orders")
@Data
@NoArgsConstructor
public class Truck {

    public enum Condition { OK, BROKEN }

    /**
     * Average truck speed (km/h)
     */
    public static final int AVG_TRUCK_SPEED = 80;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Pattern(regexp = "[A-Z]{2}\\d{5}")
    @Column(name = "name")
    private String name;

    @Min(1)
    @Column(name = "max_drivers")
    private int maxDrivers;

    @Column(name = "capacity_kg")
    @Min(1)
    private int capacityKg;

    @Column(name = "condition")
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "truck")
    private Set<Order> orders;


    public int getCapacityKg() {
        return capacityKg;
    }

    public double getCapacityTon() {
        return capacityKg / 1000;
    }

    public void setCapacityTon(double capacityTon) {
        this.setCapacityKg((int)(capacityTon / 1000));
    }

    public void setCapacityKg(int capacityKg) {
        this.capacityKg = capacityKg;
    }

    @Override
    public int hashCode() {
        return (new HashCodeBuilder())
                .append(name)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Truck))
            return false;
        if (obj == this)
            return true;

        Truck rhs = (Truck) obj;
        return (new EqualsBuilder())
                .append(name, rhs.name)
                .build();
    }

}
