/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.assignment;

import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.api.action.dto.DriverAssignmentDTO;
import com.tsystems.javaschool.logiweb.dao.entities.Order;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Igor Avdeev on 9/15/16.
 */
public class ListAssignmentsAction extends JsonAction {
    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers) throws IOException {

        if (req.getParameter("personalNumber") == null) {
            return JsonResult.list(Collections.emptyList());
        }

        // save personal number per session
        req.getSession().setAttribute("personalNumber", req.getParameter("personalNumber"));

        Collection<Order> orders = managers.getOrderManager()
                .findDriverAssignments(req.getParameter("personalNumber"));

        List<DriverAssignmentDTO> list = new LinkedList<>();

        for (Order o : orders) {
            list.add(new DriverAssignmentDTO(
                    o.getId(),
                    o.getDrivers().stream()
                            .map(d -> d.getPersonalCode() + " (" + d.getFirstName() + ")")
                            .collect(Collectors.toList()),
                    o.getWaypoints().stream()
                            .map(w -> new DriverAssignmentDTO.WaypointDTO(
                                    w.getCity().getName(),
                                    w.getOperation(),
                                    w.getCargo().getName()
                            )).collect(Collectors.toList()),
                    o.getTruck().getName(),
                    o.getWaypoints().first().getCity().getName() + " -> " + o.getWaypoints().last().getCity().getName(),
                    o.isCompleted() ? "Completed" : "Not completed"
            ));
        }

        return JsonResult.list(list);
    }
}
