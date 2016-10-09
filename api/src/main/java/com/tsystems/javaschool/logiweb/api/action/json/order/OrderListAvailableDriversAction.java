/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.order;

import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.api.action.dto.driver.DriverJsonDTO;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 9/11/16.
 */
public class OrderListAvailableDriversAction extends JsonAction {


    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers) throws IOException {

        int cityId = Integer.parseInt(req.getParameter("cityId"));
        int maxDrivers = Integer.parseInt(req.getParameter("maxDrivers"));
        int routeLength = Integer.parseInt(req.getParameter("routeLength"));

        int tripHours = managers.getDriverManager().calculateTripDuration(routeLength, maxDrivers);

        LocalDateTime expectedArrival = LocalDateTime.now().plusHours(tripHours);

        List<DriverJsonDTO> driversList = managers
                .getDriverManager()
                .findDriversForTrip(cityId, LocalDateTime.now(), expectedArrival)
                .stream()
                .map(DriverJsonDTO::map)
                .collect(Collectors.toList());

        return JsonResult.list(driversList);
    }
}
