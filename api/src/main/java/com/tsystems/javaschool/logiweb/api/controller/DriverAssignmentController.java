package com.tsystems.javaschool.logiweb.api.controller;

import com.tsystems.javaschool.logiweb.service.exception.business.BusinessLogicException;
import com.tsystems.javaschool.logiweb.service.facade.DriverAssignmentFacade;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.model.DriverUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/assignments")
public class DriverAssignmentController {

    @Autowired
    private DriverAssignmentFacade assignmentFacade;

    @Autowired
    private DriverManager driverManager;

    @GetMapping
    public String list(Model modelUi) throws BusinessLogicException {

        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new AccessDeniedException("User role is unknown.");
        }

        DriverUserModel principal = (DriverUserModel)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assignmentFacade.findCurrentDriverAssignment(principal.getDriverId());


//        modelUi.addAttribute("orderId", driverManager)
//
//        Collection<Order> orders = orderManager.findDriverAssignments(req.getParameter("personalNumber"));
//
//        List<DriverAssignmentDTO> list = new LinkedList<>();
//
//        for (Order o : orders) {
//            list.add(new DriverAssignmentDTO(
//                    o.getId(),
//                    o.getDrivers().stream()
//                            .map(d -> d.getPersonalCode() + " (" + d.getFirstName() + ")")
//                            .collect(Collectors.toList()),
//                    o.getWaypoints().stream()
//                            .map(w -> new DriverAssignmentDTO.WaypointDTO(
//                                    w.getCity().getName(),
//                                    w.getOperation(),
//                                    w.getCargo().getName()
//                            )).collect(Collectors.toList()),
//                    o.getTruck().getName(),
//                    o.getWaypoints().first().getCity().getName() + " -> " + o.getWaypoints().last().getCity().getName(),
//                    o.isCompleted() ? "Completed" : "Not completed"
//            ));
//        }
//
//        return JsonResult.list(list);


        return "assignment.list";
    }
}
