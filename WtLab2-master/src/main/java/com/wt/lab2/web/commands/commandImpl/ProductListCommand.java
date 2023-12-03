package com.wt.lab2.web.commands.commandImpl;

import com.wt.lab2.model.dao.JewelryDao;
import com.wt.lab2.model.dao.impl.JdbcJewelryDao;
import com.wt.lab2.model.exceptions.DaoException;
import com.wt.lab2.web.JspPageName;
import com.wt.lab2.web.commands.ICommand;
import com.wt.lab2.web.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author dana
 * @version 1.0
 * Command to show list of products
 */
public class ProductListCommand implements ICommand {
    private final JewelryDao jewelryDao = JdbcJewelryDao.getInstance();
    private static final String QUERY_PARAMETER = "query";
    private static final String JEWELRIES_ATTRIBUTE = "jewelries";
    private static final String PAGE_PARAMETER = "page";
    private static final int JEWELRIES_ON_PAGE = 10;

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
            request.setAttribute(JEWELRIES_ATTRIBUTE, jewelryDao.getJewelry(((pageNumber == null ? 1 : Integer.parseInt(pageNumber)) - 1) * JEWELRIES_ON_PAGE, JEWELRIES_ON_PAGE, request.getParameter(QUERY_PARAMETER)));
        } catch (DaoException e) {
            throw new CommandException(e.getMessage());
        }
        return JspPageName.PRODUCT_LIST_JSP;
    }
}
