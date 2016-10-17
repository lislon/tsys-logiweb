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
public class Truck {

    public enum Condition { OK, BROKEN }


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

    @OneToOne(mappedBy = "truck")
    private Order currentOrder;


    public int getCapacityKg() {
        return capacityKg;
    }

    public double getCapacityTon() {
        return capacityKg / 1000.0;
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

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxDrivers=" + maxDrivers +
                ", capacityKg=" + capacityKg +
                ", condition=" + condition +
                ", city=" + city +
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

    public int getMaxDrivers() {
        return maxDrivers;
    }

    public void setMaxDrivers(int maxDrivers) {
        this.maxDrivers = maxDrivers;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }
}
