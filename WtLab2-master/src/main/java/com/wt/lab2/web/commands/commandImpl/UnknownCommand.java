package com.wt.lab2.web.commands.commandImpl;

import com.wt.lab2.web.JspPageName;
import com.wt.lab2.web.commands.ICommand;
import com.wt.lab2.web.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author nekit
 * @version 1.0
 * Command to unknown name
 */
public class UnknownCommand implements ICommand {
    /**
     * Return error page
     *
     * @param request http request
     * @return return error page jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return JspPageName.ERROR_PAGE;
    }
}
