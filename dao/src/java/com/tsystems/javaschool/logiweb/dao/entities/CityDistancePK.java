/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
public class CityDistancePK implements Serializable {
    private int srcCityId;
    private int dstCityId;

    @Column(name = "src_city_id")
    @Id
    public int getSrcCityId() {
        return srcCityId;
    }

    public void setSrcCityId(int srcCityId) {
        this.srcCityId = srcCityId;
    }

    @Column(name = "dst_city_id")
    @Id
    public int getDstCityId() {
        return dstCityId;
    }

    public void setDstCityId(int dstCityId) {
        this.dstCityId = dstCityId;
    }
}
