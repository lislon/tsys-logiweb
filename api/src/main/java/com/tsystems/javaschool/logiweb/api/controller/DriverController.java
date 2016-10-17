/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller;

import com.tsystems.javaschool.logiweb.api.action.dto.driver.DriverEditDTO;
import com.tsystems.javaschool.logiweb.api.helper.FlashMessageSender;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
import com.tsystems.javaschool.logiweb.service.dto.UserDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.BusinessLogicException;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.facade.DriverCreatorFacade;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.manager.UserManager;
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

    @Autowired
    private UserManager userManager;

    @Autowired
    private DriverCreatorFacade driverCreatorFacade;

    @GetMapping
    public String list() {
        return "driver.list";
    }

    @GetMapping("/new")
    public String create(Model modelUi) {
        modelUi.addAttribute("model", new DriverEditDTO());
        return "driver.edit";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model modelUi) throws EntityNotFoundException {

        DriverDTO driverDTO = manager.findDto(id);
        UserDTO userDTO;
        if (driverDTO.getUserId() != null) {
            userDTO = userManager.findUserDtoById(driverDTO.getUserId());
        } else {
            userDTO = new UserDTO();
        }

        modelUi.addAttribute("model", DriverEditDTO.buildFrom(driverDTO, userDTO));
        return "driver.edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("model") @Valid DriverEditDTO driverDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) throws BusinessLogicException {

        if (bindingResult.hasErrors()) {
            return "driver.edit";
        }

        driverCreatorFacade.updateDriver(driverDTO.extractDriverDTO(), driverDTO.extractUserDTO());

        return FlashMessageSender.redirectToListWithSuccess(
                DriverController.class,
                redirectAttributes,
                "Successfully updated driver '" + driverDTO.getPersonalCode() + "'");
    }

    @PostMapping("/new")
    public String newDriver(@ModelAttribute("model") @Valid DriverEditDTO driverDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) throws BusinessLogicException {

        if (bindingResult.hasErrors()) {
            return "driver.edit";
        }

        driverCreatorFacade.createDriver(driverDTO.extractDriverDTO(), driverDTO.extractUserDTO());

        return FlashMessageSender.redirectToListWithSuccess(
                DriverController.class,
                redirectAttributes,
                "Successfully added driver '" + driverDTO.getPersonalCode() + "'");
    }

}
