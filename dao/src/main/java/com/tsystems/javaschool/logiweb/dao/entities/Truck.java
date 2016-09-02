/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Entity
@Table(name = "trucks")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Truck that = (Truck) o;
//
//        if (id != that.id) return false;
//        if (maxDuty != that.maxDuty) return false;
//        if (capacityKg != that.capacityKg) return false;
//        if (name != null ? !name.equals(that.name) : that.name != null) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id;
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + maxDuty;
//        result = 31 * result + capacityKg;
//        return result;
//    }
}
