package com.tsystems.javaschool.logiweb.service.facade;

import com.tsystems.javaschool.logiweb.service.dto.CargoDTO;
import com.tsystems.javaschool.logiweb.service.dto.FullOrderDataDTO;
import com.tsystems.javaschool.logiweb.service.dto.WaypointDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.BusinessLogicException;
import com.tsystems.javaschool.logiweb.service.manager.CargoManager;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;
import com.tsystems.javaschool.logiweb.service.manager.OrderWorkflowManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderCreatorFacade {

    @Autowired
    private OrderManager manager;

    @Autowired
    private OrderWorkflowManager orderWorkflowManager;

    @Autowired
    private CargoManager cargoManager;

    public int createOrder(FullOrderDataDTO route) throws BusinessLogicException {
        int orderId = manager.createOrder();

        List<Integer> cargoIds = new LinkedList<>();

        // set cargo
        for (CargoDTO cargoDto : route.getCargoes()) {
            cargoIds.add(cargoManager.createCargo(cargoDto, orderId));
        }

        // set waypoints
        List<WaypointDTO> waypointDTOs = route.getWaypoints()
                .stream()
                .map(dto -> {
                    int cargoId = cargoIds.get(dto.getCargoNo());
                    return new WaypointDTO(cargoId, dto.getCityId(), dto.getOperation());
                }).collect(Collectors.toList());

        manager.updateWaypoints(orderId, waypointDTOs);
        orderWorkflowManager.assignTruckAndDrivers(orderId, route.getSelectedTruckId(), route.getSelectedDrivers());
        return orderId;
    }
}
