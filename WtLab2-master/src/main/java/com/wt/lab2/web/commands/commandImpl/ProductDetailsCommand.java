package com.wt.lab2.web.commands.commandImpl;

import com.wt.lab2.model.dao.JewelryDao;
import com.wt.lab2.model.dao.impl.JdbcJewelryDao;
import com.wt.lab2.model.entities.jewelry.Jewelry;
import com.wt.lab2.model.exceptions.DaoException;
import com.wt.lab2.web.JspPageName;
import com.wt.lab2.web.commands.ICommand;
import com.wt.lab2.web.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author dana
 * @version 1.0
 * Command to get product details page
 */
public class ProductDetailsCommand implements ICommand {
    private final JewelryDao jewelryDao = JdbcJewelryDao.getInstance();
    private static final String JEWELRY_ATTRIBUTE = "jewelry";

    /**
     * Return product details page of current car
     * @param request http request
     * @return product details page jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Jewelry jewelry;
        try {
            if (request.getParameter("jewelry_id") == null){
                jewelry = jewelryDao.getJewelryById(Long.parseLong(request.getAttribute("jewelry_id").toString())).orElse(null);
            } else {
                jewelry = jewelryDao.getJewelryById(Long.valueOf(request.getParameter("jewelry_id"))).orElse(null);
            }
        } catch (DaoException e) {
            throw new CommandException(e.getMessage());
        }
        if (jewelry != null) {
            request.setAttribute(JEWELRY_ATTRIBUTE, jewelry);
            return JspPageName.PRODUCT_PAGE;
        }
        else{
            return JspPageName.PRODUCT_NOT_FOUND_PAGE;
        }
    }
}
