package com.tsystems.javaschool.logiweb.service.dto.converter;

import com.tsystems.javaschool.logiweb.dao.entities.User;
import com.tsystems.javaschool.logiweb.service.dto.UserDTO;

public class UserDTOConverter {
    public static UserDTO copyToDto(User from) {

        UserDTO to = new UserDTO();

        to.setEmail(from.getEmail());

        return to;
    }
}
