package com.wt.lab2.model.exceptions;

/**
 * @author dana
 * @version 1.0
 */
public class CloneException extends RuntimeException {
    /**
     * Place message of exception
     * @param message message of exception
     */
    public CloneException(String message) {
        super(message);
    }
}
