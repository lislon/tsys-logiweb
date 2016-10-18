package com.tsystems.javaschool.logiweb.api.controller;

import org.springframework.stereotype.Controller;

@Controller
public class DriverDashboardController {
//     if (req.getParameter("personalNumber") == null) {
//        return JsonResult.list(Collections.emptyList());
//    }
//
//    // save personal number per session
//        req.getSession().setAttribute("personalNumber", req.getParameter("personalNumber"));
//
//    Collection<Order> orders = managers.getOrderManager()
//            .findDriverAssignments(req.getParameter("personalNumber"));
//
//    List<DriverAssignmentDTO> list = new LinkedList<>();
//
//        for (Order o : orders) {
//        list.add(new DriverAssignmentDTO(
//                o.getId(),
//                o.getDrivers().stream()
//                        .map(d -> d.getPersonalCode() + " (" + d.getFirstName() + ")")
//                        .collect(Collectors.toList()),
//                o.getWaypoints().stream()
//                        .map(w -> new DriverAssignmentDTO.CargoWaypointDTO(
//                                w.getCity().getName(),
//                                w.getOperation(),
//                                w.getCargo().getName()
//                        )).collect(Collectors.toList()),
//                o.getTruck().getName(),
//                o.getWaypoints().first().getCity().getName() + " -> " + o.getWaypoints().last().getCity().getName(),
//                o.isCompleted() ? "Completed" : "Not completed"
//        ));
//    }
//
//        return JsonResult.list(list);
}
