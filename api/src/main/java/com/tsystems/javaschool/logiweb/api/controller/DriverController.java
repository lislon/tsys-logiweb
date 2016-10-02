/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller;

import com.tsystems.javaschool.logiweb.api.helper.FlashMessageSender;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by Igor Avdeev on 10/4/16.
 */
@Controller
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverManager manager;

    @GetMapping
    public String list() {
        return "driver.list";
    }

    @GetMapping("/new")
    public String create(Model modelUi) {
        modelUi.addAttribute("model", new DriverDTO());
        return "driver.edit";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model modelUi) throws EntityNotFoundException {
        modelUi.addAttribute("model", manager.findDto(id));
        return "driver.edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("model") @Valid DriverDTO driverDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) throws EntityNotFoundException {

        if (bindingResult.hasErrors()) {
            return "driver.edit";
        }

        manager.update(id, driverDTO);

        return FlashMessageSender.redirectToListWithSuccess(
                DriverController.class,
                redirectAttributes,
                "Successfully updated driver '" + driverDTO.getPersonalCode() + "'");
    }
    @PostMapping("/new")
    public String newDriver(@ModelAttribute("model") @Valid DriverDTO driverDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) throws EntityNotFoundException {

        if (bindingResult.hasErrors()) {
            return "driver.edit";
        }

        manager.create(driverDTO);

        return FlashMessageSender.redirectToListWithSuccess(
                DriverController.class,
                redirectAttributes,
                "Successfully added driver '" + driverDTO.getPersonalCode() + "'");
    }

}
