package com.tsystems.javaschool.models;

import com.tsystems.javaschool.entities.Driver;
import com.tsystems.javaschool.repos.DriverRepository;

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
        repo.add(driver);
    }

    public List<Driver> findAllDrivers()
    {
        return repo.findAll();
    }
}
