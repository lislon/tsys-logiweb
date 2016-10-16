package com.tsystems.javaschool.logiweb.service.manager;

import com.tsystems.javaschool.logiweb.dao.entities.Account;
import com.tsystems.javaschool.logiweb.service.exception.DuplicateKeyException;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.InvalidStateException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    
    /**
     * Create new logiweb user. 
     * 
     * @param userName (email)
     * @param pass
     * @param role in system 
     * @return
     * @throws DuplicateKeyException if user with this name already exist
     */
    int createNewUser(String userName, String pass, Account.UserRole role) throws DuplicateKeyException, InvalidStateException;
    
    /**
     * Find user by id.
     * 
     * @param id
     * @return user or null
     */
    Account findUserById(int id) throws EntityNotFoundException;
}
