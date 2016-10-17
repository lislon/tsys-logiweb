package com.tsystems.javaschool.logiweb.service.facade;

import com.tsystems.javaschool.logiweb.dao.entities.User;
import com.tsystems.javaschool.logiweb.service.dto.DriverDTO;
import com.tsystems.javaschool.logiweb.service.dto.UserDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.DuplicateEntityException;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.business.InvalidStateException;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DriverCreatorFacade {

    @Autowired
    private DriverManager driverManager;

    @Autowired
    private UserManager userManager;

    /**
     * Creates a driver and account for logging-in
     * @param driver
     * @param user
     * @return
     * @throws EntityNotFoundException
     * @throws InvalidStateException
     * @throws DuplicateEntityException
     */
    public int createDriver(DriverDTO driver, UserDTO user) throws EntityNotFoundException, InvalidStateException, DuplicateEntityException {
        int userId = userManager.createNewUser(user.getEmail(), user.getPassword(), User.UserRole.ROLE_DRIVER);

        driver.setUserId(userId);
        return driverManager.create(driver);
    }

    public void updateDriver(DriverDTO driver, UserDTO user) throws EntityNotFoundException, InvalidStateException, DuplicateEntityException {
        userManager.update(driver.getUserId(), user);
        driverManager.update(driver.getId(), driver);
    }

    public void deleteDriverAndUser(int driverId) throws EntityNotFoundException, InvalidStateException, DuplicateEntityException {
        driverManager.deleteDriver(driverId);
        // TODO Implement deletion of user
    }

}
