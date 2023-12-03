package com.wt.lab2.web.commands.commandImpl;

import com.wt.lab2.model.dao.CarDao;
import com.wt.lab2.model.dao.impl.JdbcCarDao;
import com.wt.lab2.model.exceptions.DaoException;
import com.wt.lab2.web.JspPageName;
import com.wt.lab2.web.commands.ICommand;
import com.wt.lab2.web.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author nekit
 * @version 1.0
 * Command to show list of products
 */
public class ProductListCommand implements ICommand {
    private final CarDao carDao = JdbcCarDao.getInstance();
    private static final String QUERY_PARAMETER = "query";
    private static final String CARS_ATTRIBUTE = "cars";
    private static final String PAGE_PARAMETER = "page";
    private static final int carS_ON_PAGE = 10;

    /**
     * Get product list and return product list jsp
     *
     * @param request http request
     * @return product list jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String pageNumber = request.getParameter(PAGE_PARAMETER);
        try {
            request.setAttribute(CARS_ATTRIBUTE, carDao.getCars(((pageNumber == null ? 1 : Integer.parseInt(pageNumber)) - 1) * carS_ON_PAGE, carS_ON_PAGE, request.getParameter(QUERY_PARAMETER)));
        } catch (DaoException e) {
            throw new CommandException(e.getMessage());
        }
        return JspPageName.PRODUCT_LIST_JSP;
    }
}
