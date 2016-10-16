/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller.rest;

import com.tsystems.javaschool.logiweb.service.exception.BusinessLogicException;
import com.tsystems.javaschool.logiweb.service.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Exception handlers for rest controllers, which returns a valid json object with description of error.
 */
public abstract class BaseRestController {
    @ExceptionHandler(BusinessLogicException.class)
    public Map<String, String> businessLogicException(HttpServletResponse response, Exception exception) {
        Map<String, String> result = new HashMap<>();
        result.put("errorText", exception.getMessage());

        if (exception instanceof EntityNotFoundException) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return result;
    }

    @ExceptionHandler(Exception.class)
    public Map<String, String> genericException(HttpServletResponse response, Exception exception) {
        Map<String, String> result = new HashMap<>();
        result.put("errorText", exception.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return result;
    }

}
