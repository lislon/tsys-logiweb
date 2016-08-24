package com.tsystems.javaschool.repos;

import com.tsystems.javaschool.entities.Driver;


/**
 * Created by Igor Avdeev on 8/23/16.
 */
public class DriverRepository extends BaseRepository<Driver> {

    public DriverRepository() {
        super(Driver.class);
    }
}
