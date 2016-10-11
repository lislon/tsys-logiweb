/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller;

import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Igor Avdeev on 10/4/16.
 */
@Controller
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverManager manager;

    @GetMapping
    public String index() {
        return "driver.list";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model modelUi) throws EntityNotFoundException {

        modelUi.addAttribute("driverModel", manager.findDto(id));

        return "driver.edit";
    }
    @GetMapping("/new")
    public String create() {
        return "driver.edit";
    }
}
