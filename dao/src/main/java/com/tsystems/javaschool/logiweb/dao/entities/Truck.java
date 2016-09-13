/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Entity
@Table(name = "trucks")
@Data
@NoArgsConstructor
public class Truck {

    public enum Condition { OK, BROKEN }

    @Id
    @Column(name = "id")
    private int id;

    @Pattern(regexp = "[A-Z]{2}\\d{5}")
    @Column(name = "name")
    private String name;

    @Min(1)
    @Column(name = "max_drivers")
    private int maxDrivers;

    @Column(name = "capacity_kg")
    private int capacityKg;

    @Column(name = "condition")
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

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
}
