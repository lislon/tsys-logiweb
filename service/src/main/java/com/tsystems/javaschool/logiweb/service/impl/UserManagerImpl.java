package com.tsystems.javaschool.logiweb.service.impl;


import com.tsystems.javaschool.logiweb.dao.entities.User;
import com.tsystems.javaschool.logiweb.dao.repos.UserRepository;
import com.tsystems.javaschool.logiweb.service.dto.UserDTO;
import com.tsystems.javaschool.logiweb.service.exception.Md5DigestException;
import com.tsystems.javaschool.logiweb.service.exception.business.DuplicateEntityException;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import com.tsystems.javaschool.logiweb.service.exception.business.InvalidStateException;
import com.tsystems.javaschool.logiweb.service.manager.UserManager;
import com.tsystems.javaschool.logiweb.service.model.DriverUserModel;
import com.tsystems.javaschool.logiweb.service.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserManagerImpl implements UserDetailsService, UserManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserManagerImpl.class);

    private UserRepository repo;

    @Autowired
    public UserManagerImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public UserModel loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = repo.findByEmail(userName);
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        } else {
            return buildSecurityUserFromUserEntity(user);
        }
    }
    
    @Override
    public int createNewUser(String userName, String pass, User.UserRole role) throws DuplicateEntityException, InvalidStateException {
        if (userName == null || userName.isEmpty()) {
            throw new InvalidStateException("Username can't be empty.");
        }
        
        User userWithSameMail = repo.findByEmail(userName);
        if (userWithSameMail != null) {
            throw new DuplicateEntityException(
                    "User with username: " + userName + " already exist.");
        }

        User newUser = new User();
        newUser.setEmail(userName);
        newUser.setPasswordMd5(getMD5Hash(pass));
        newUser.setRole(role);

        repo.saveAndFlush(newUser);

        LOG.info("User #" + newUser.getId() + " " + userName + " created");
        return newUser.getId();
    }
    
    private UserModel buildSecurityUserFromUserEntity(User userEntity) {
        String username = userEntity.getEmail();
        String password = userEntity.getPasswordMd5();
        GrantedAuthority userRole = new SimpleGrantedAuthority(userEntity
                .getRole().name());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(userRole);

        if (userEntity.getRole() == User.UserRole.ROLE_DRIVER) {
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
             throw new Md5DigestException("MD5 hashing failed", e);
         }
     }

    @Override
    @Transactional(readOnly = true)
    public User findOneOrFail(int id) throws EntityNotFoundException {
        User user = repo.findOne(id);
        if (user == null) {
            throw new EntityNotFoundException("UserAccount", id);
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findUserDtoById(int id) throws EntityNotFoundException {
        UserDTO to = new UserDTO();
        to.setEmail(findOneOrFail(id).getEmail());
        return to;
    }

    @Override
    public void update(int userId, UserDTO updateData) throws DuplicateEntityException, EntityNotFoundException {
        User user = this.findOneOrFail(userId);

        // if user changes email check if we have duplicates
        if (!updateData.getEmail().equals(user.getEmail())) {
            if (repo.findByEmail(updateData.getEmail()) != null) {
                throw new DuplicateEntityException("User with username: " + updateData.getEmail() + " already exist.");
            }

            user.setEmail(updateData.getEmail());
        }

        if (updateData.getPassword() != null && !updateData.getPassword().isEmpty()) {
            user.setPasswordMd5(getMD5Hash(updateData.getPassword()));
        }
        repo.save(user);
    }

}
