/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.dao.repos.DriverRepository;

import java.util.List;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public class DriverManager {
    private DriverRepository repo = null;

    public DriverManager(DriverRepository repo) {
        this.repo = repo;
    }

    public void addDriver(Driver driver)
    {
        repo.save(driver);
    }

    public List<Driver> findAllDrivers()
    {
        return repo.findAll();
    }
}
