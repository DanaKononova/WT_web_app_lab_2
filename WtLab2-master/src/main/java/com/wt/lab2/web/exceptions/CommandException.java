package com.wt.lab2.web.exceptions;

/**
 * @author dana
 * @version 1.0
 * Exception in layer of commands
 */
public class CommandException extends ProjectException {
    private static final long serialVersionUID = 1L;

    public CommandException(String msg) {
        super(msg);
    }

    public CommandException(String msg, Exception e) {
        super(msg, e);
    }

}
