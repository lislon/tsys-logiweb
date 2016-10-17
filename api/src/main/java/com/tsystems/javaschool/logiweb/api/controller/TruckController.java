/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller;

import com.tsystems.javaschool.logiweb.api.helper.FlashMessageSender;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.DuplicateEntityException;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/trucks")
public class TruckController {
    @Autowired
    private TruckManager manager;

    @GetMapping
    public String list() {
        return "truck.list";
    }

    @GetMapping("/new")
    public String create(Model modelUi) {

        modelUi.addAttribute("model", new TruckDTO());

        return "truck.edit";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model modelUi) throws EntityNotFoundException {

        modelUi.addAttribute("model", manager.findDto(id));

        return "truck.edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("model") @Valid TruckDTO dto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) throws EntityNotFoundException {

        if (bindingResult.hasErrors()) {
            return "truck.edit";
        }

        try {
            manager.update(id, dto);
        } catch (DuplicateEntityException e) {
            bindingResult.addError(new ObjectError("name", "An truck with same name already exists"));
            return "truck.edit";
        }

        return FlashMessageSender.redirectToListWithSuccess(TruckController.class, redirectAttributes,
                "Successfully updated truck '" + dto.getName() + "'");
    }

    @PostMapping("/new")
    public String newTruck(@ModelAttribute("model") @Valid TruckDTO dto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws EntityNotFoundException {

        if (bindingResult.hasErrors()) {
            return "truck.edit";
        }

        try {
            manager.create(dto);
        } catch (DuplicateEntityException e) {
            bindingResult.addError(new ObjectError("name", "An truck with same name already exists"));
            return "truck.edit";
        }

        return FlashMessageSender.redirectToListWithSuccess(TruckController.class, redirectAttributes,
                "Successfully added truck '" + dto.getName() + "'");
    }
}
