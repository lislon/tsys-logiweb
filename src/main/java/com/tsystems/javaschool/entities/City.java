package com.tsystems.javaschool.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Entity(name = "cities")
public class City {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "lat")
    private double lat;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Column(name = "lng")
    private double lng;
}
