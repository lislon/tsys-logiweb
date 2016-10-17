/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.controller.rest;

import com.tsystems.javaschool.logiweb.service.exception.business.BusinessLogicException;
import com.tsystems.javaschool.logiweb.service.exception.business.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Exception handlers for rest controllers, which returns plain text with error for jquery.
 */
public abstract class BaseRestController {
    @ExceptionHandler(BusinessLogicException.class)
    public void businessLogicException(HttpServletResponse response, Exception exception) throws IOException {
        response.getWriter().write(exception.getMessage());

        if (exception instanceof EntityNotFoundException) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    @ExceptionHandler(Exception.class)
    public void genericException(HttpServletResponse response, Exception exception) throws IOException {
        response.getWriter().write("Error 500:" + exception.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
