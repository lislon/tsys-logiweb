/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.exception;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
public class DuplicateKeyException extends BusinessLogicException {
    public DuplicateKeyException(String message) {
        super(message);
    }
}
