/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import javax.persistence.*;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
@Entity
@Table(name = "cities_distances")
@IdClass(CityDistancePK.class)
public class CityDistance {
    private int srcCityId;
    private int dstCityId;
    private Integer distance;

    @Id
    @Column(name = "src_city_id")
    public int getSrcCityId() {
        return srcCityId;
    }

    public void setSrcCityId(int srcCityId) {
        this.srcCityId = srcCityId;
    }

    @Id
    @Column(name = "dst_city_id")
    public int getDstCityId() {
        return dstCityId;
    }

    public void setDstCityId(int dstCityId) {
        this.dstCityId = dstCityId;
    }

    @Basic
    @Column(name = "distance")
    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
