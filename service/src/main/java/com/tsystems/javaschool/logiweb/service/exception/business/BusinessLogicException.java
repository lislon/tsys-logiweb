/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.exception.business;

/**
 * Created by Igor Avdeev on 9/13/16.
 */
public abstract class BusinessLogicException extends Exception {
    public BusinessLogicException(String message) {
        super(message);
    }
}
