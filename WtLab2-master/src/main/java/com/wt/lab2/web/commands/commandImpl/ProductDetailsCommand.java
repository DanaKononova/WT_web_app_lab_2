package com.wt.lab2.web.commands.commandImpl;

import com.wt.lab2.model.dao.CarDao;
import com.wt.lab2.model.dao.impl.JdbcCarDao;
import com.wt.lab2.model.entities.car.Car;
import com.wt.lab2.model.exceptions.DaoException;
import com.wt.lab2.web.JspPageName;
import com.wt.lab2.web.commands.ICommand;
import com.wt.lab2.web.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author nekit
 * @version 1.0
 * Command to get product details page
 */
public class ProductDetailsCommand implements ICommand {
    private final CarDao carDao = JdbcCarDao.getInstance();
    private static final String CAR_ATTRIBUTE = "car";

    /**
     * Return product details page of current car
     * @param request http request
     * @return product details page jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Car car;
        try {
            if (request.getParameter("car_id") == null){
                car = carDao.getCarById(Long.parseLong(request.getAttribute("car_id").toString())).orElse(null);
            } else {
                car = carDao.getCarById(Long.valueOf(request.getParameter("car_id"))).orElse(null);
            }
        } catch (DaoException e) {
            throw new CommandException(e.getMessage());
        }
        if (car != null) {
            request.setAttribute(CAR_ATTRIBUTE, car);
            return JspPageName.PRODUCT_PAGE;
        }
        else{
            return JspPageName.PRODUCT_NOT_FOUND_PAGE;
        }
    }
}
