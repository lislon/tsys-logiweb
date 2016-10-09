/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller;

import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/edit/{id2}")
    public String edit(@PathVariable("id2") int id2, Model modelUi) throws EntityNotFoundException {

        modelUi.addAttribute("driverModel", manager.findDto(id2));

        return "driver.edit";
    }
}
