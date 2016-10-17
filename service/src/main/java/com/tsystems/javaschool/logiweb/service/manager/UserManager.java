package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.User;
import com.tsystems.javaschool.logiweb.service.dto.UserDTO;
import com.tsystems.javaschool.logiweb.service.exception.business.DuplicateEntityException;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.business.InvalidStateException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserManager extends UserDetailsService {
    
    /**
     * Create new logiweb user. 
     * 
     * @param userName (email)
     * @param pass
     * @param role in system 
     * @return
     * @throws DuplicateEntityException if user with this name already exist
     */
    int createNewUser(String userName, String pass, User.UserRole role) throws DuplicateEntityException, InvalidStateException;
    
    /**
     * Find user by id.
     * 
     * @param id
     * @return user or null
     */
    User findOneOrFail(int id) throws EntityNotFoundException;

    UserDTO findUserDtoById(int id) throws EntityNotFoundException;

    void update(int userId, UserDTO userDTO) throws DuplicateEntityException, EntityNotFoundException;
}
