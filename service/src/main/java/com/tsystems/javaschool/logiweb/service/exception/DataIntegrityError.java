package com.tsystems.javaschool.logiweb.service.exception;

/**
 * Throws when service layer unable to find entity id, that was just loaded from database.
 */
public class DataIntegrityError extends RuntimeException {
    public DataIntegrityError(String message, Throwable cause) {
        super(message, cause);
    }
}
