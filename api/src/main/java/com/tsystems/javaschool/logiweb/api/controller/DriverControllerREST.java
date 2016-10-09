/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller;

import com.tsystems.javaschool.logiweb.api.action.dto.driver.DriverUpdateDTO;
import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
import com.tsystems.javaschool.logiweb.service.exception.BusinessLogicException;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * Created by Igor Avdeev on 10/4/16.
 */
@RestController
@RequestMapping("/api/drivers")
public class DriverControllerREST {

    @Autowired
    private DriverManager manager;

    @GetMapping
    public Iterable<Driver> list() {
        return manager.findAll();
    }

    @PostMapping("/{id}")
    public void update(@PathVariable("id") int id, @Validated DriverDTO driver) throws EntityNotFoundException {
        manager.update(id, driver);
    }

    @GetMapping("/new")
    public int create(@Validated DriverDTO driver) throws EntityNotFoundException {
        return manager.create(driver);
    }

    // Error handling
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Driver or city not found")
    @ExceptionHandler(EntityNotFoundException.class)
    public void notfound() { }
}
