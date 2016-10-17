/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller.rest;

import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.business.InvalidStateException;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trucks/api")
public class TruckRestController extends BaseRestController {
    @Autowired
    private TruckManager manager;

    @GetMapping()
    public @ResponseBody Iterable<TruckDTO> apiList() {
        return manager.findAllTrucks();
    }

    @DeleteMapping("/{id}")
    public void apiDelete(@PathVariable("id") int id) throws EntityNotFoundException, InvalidStateException {
        manager.deleteTruck(id);
    }
}
