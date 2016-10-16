/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller.rest;

import com.tsystems.javaschool.logiweb.api.action.dto.driver.DriverJsonDTO;
import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.service.exception.BusinessLogicException;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.helper.RouteCalculator;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 10/4/16.
 */
@RestController
@RequestMapping("/drivers/api")
public class DriverRestController extends BaseRestController {

    @Autowired
    private DriverManager manager;

    @Autowired
    private RouteCalculator routeCalculator;

    @GetMapping("")
    public @ResponseBody Iterable<Driver> apiList() {
        return manager.findAll();
    }

    @GetMapping("/exception")
    public @ResponseBody void exception() throws BusinessLogicException {
        throw new EntityNotFoundException("Hello!");
    }

    @DeleteMapping("/{id}")
    public void apiDelete(@PathVariable("id") int id) throws EntityNotFoundException {
        if (!manager.delete(id)) {
            throw new EntityNotFoundException("Driver with id=" + id + " is not found");
        }
    }

    /**
     * List available drivers to have a trip specified lenght {@code routeLenght}
     * from city {@code cityId} on truck with {@code numCoDrivers} co-drivers
     * @param cityId
     * @param numCoDrivers
     * @param routeLength
     * @return
     */
    @GetMapping("/available")
    public List<DriverJsonDTO> listAvaliableDrivers(@RequestParam("cityId") int cityId,
                                                    @RequestParam("maxDrivers") int numCoDrivers,
                                                    @RequestParam("routeLength") int routeLength) {


        double tripHours = routeCalculator.getRouteDuration(routeLength, numCoDrivers);

        LocalDateTime expectedArrival = LocalDateTime.now().plusHours((long)tripHours);

        List<DriverJsonDTO> driversList = manager
                .findDriversForTrip(cityId, LocalDateTime.now(), expectedArrival)
                .stream()
                .map(DriverJsonDTO::map)
                .collect(Collectors.toList());

        return driversList;
    }
}
