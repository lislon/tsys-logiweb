package com.tsystems.javaschool.logiweb.service.impl;


import com.tsystems.javaschool.logiweb.dao.entities.Account;
import com.tsystems.javaschool.logiweb.dao.repos.UserRepository;
import com.tsystems.javaschool.logiweb.service.exception.DuplicateKeyException;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.InvalidStateException;
import com.tsystems.javaschool.logiweb.service.manager.UserService;
import com.tsystems.javaschool.logiweb.service.model.DriverUserModel;
import com.tsystems.javaschool.logiweb.service.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository repo;
    
    @Autowired
    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserModel loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account user = repo.findByEmail(userName);
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        } else {
            return buildSecurityUserFromUserEntity(user);
        }
    }
    
    @Override
    public int createNewUser(String userName, String pass, Account.UserRole role) throws DuplicateKeyException, InvalidStateException {
        if (userName == null || userName.isEmpty()) {
            throw new InvalidStateException("Username can't be empty.");
        }
        
        Account userWithSameMail = repo.findByEmail(userName);
        if (userWithSameMail != null) {
            throw new DuplicateKeyException(
                    "User with username: " + userName + " already exist.");
        }

        Account newUser = new Account();
        newUser.setEmail(userName);
        newUser.setPasswordMd5(getMD5Hash(pass));
        newUser.setRole(role);

        repo.saveAndFlush(newUser);

        LOG.info("User #" + newUser.getId() + " " + userName + " created");
        return newUser.getId();
    }
    
    private UserModel buildSecurityUserFromUserEntity(Account userEntity) {
        String username = userEntity.getEmail();
        String password = userEntity.getPasswordMd5();
        GrantedAuthority userRole = new SimpleGrantedAuthority(userEntity
                .getRole().name());

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(userRole);

        if (userEntity.getRole() == Account.UserRole.ROLE_DRIVER) {
            return new DriverUserModel(username, password, authorities,
                    userEntity.getDriver().getId());
        } else {
            return new UserModel(username, password, authorities);
        }
    }
    
    private String getMD5Hash(String pass)  {
        try {
             MessageDigest md = MessageDigest.getInstance("MD5");
             md.reset();
             byte[] array = md.digest(pass.getBytes());
             StringBuilder sb = new StringBuilder();
             for (int i = 0; i < array.length; ++i) {
               sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
             return sb.toString();
         } catch (NoSuchAlgorithmException e) {
             throw new RuntimeException("MD5 hashing failed", e);
         }
     }

    @Override
    @Transactional
    public Account findUserById(int id) throws EntityNotFoundException {
        Account account = repo.findOne(id);
        if (account == null) {
            throw new EntityNotFoundException("UserAccount", id);
        }
        return account;
    }
    
}
