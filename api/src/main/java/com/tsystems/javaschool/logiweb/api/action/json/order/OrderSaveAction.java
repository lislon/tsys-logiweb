/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.api.action.dto.OrderDataDTO;
import com.tsystems.javaschool.logiweb.api.helper.RouteDTOConverter;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.dao.entities.OrderWaypoint;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.exception.BusinessLogicException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.SortedSet;

/**
 * Created by Igor Avdeev on 9/14/16.
 */
public class OrderSaveAction extends JsonAction {
    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        // Convert JSON string from file to Object
        OrderDataDTO orderDto = mapper.readValue(req.getReader(), OrderDataDTO.class);
        try {
            SortedSet<OrderWaypoint> waypoints = RouteDTOConverter.getOrderWaypoints(managers.getCityManager(), orderDto);
            if (req.getParameter("id") == null) {
                managers.getOrderManager().create(waypoints, orderDto.selectedTruckId, orderDto.selectedDrivers);
            } else {
                int orderId = Integer.parseInt(req.getParameter("id"));
                managers.getOrderManager().update(orderId, orderDto.selectedTruckId, orderDto.selectedDrivers);
            }
            return JsonResult.success();
        } catch (BusinessLogicException e) {
            return JsonResult.error("Invalid request " + e.getMessage());
        }
    }
}
