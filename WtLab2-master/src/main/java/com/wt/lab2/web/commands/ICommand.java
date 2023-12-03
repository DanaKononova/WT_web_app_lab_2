package com.wt.lab2.web.commands;

import com.wt.lab2.web.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author dana
 * @version 1.0
 * Interface of all commands
 */
public interface ICommand {
    /**
     * Main function of all commands
     *
     * @param request http request
     * @return jsp path
     * @throws CommandException throws when some errors during execution of command
     */
    String execute(HttpServletRequest request) throws CommandException;
}
