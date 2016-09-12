/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.exception;

/**
 * Created by Igor Avdeev on 9/13/16.
 */
abstract class BusinessLogicException extends Exception {
    public BusinessLogicException(String message) {
        super(message);
    }
}
